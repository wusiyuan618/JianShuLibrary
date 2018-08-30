package minto.com.createview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

import minto.com.createview.R;

/**
 * Created by wusy on 2017/3/13.
 */
public class VerificationCode extends View {
    /**
     * 文本
     */
    private String mContent = "";
    /**
     * 文本的颜色
     */
    private int mContentColor = Color.BLACK;
    /**
     * 文本的大小
     */
    private int mContentSize = 10;

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    /**
     * 绘制的背景颜色
     */
    private int mBackGround = Color.GREEN;
    /**
     * 边框颜色
     */
    private int mBorderColor = Color.BLACK;
    private int mBorderSize = 1;
    /**
     * 随机数函数
     */
    private Random rd;
    /**
     * 干扰项的数量（分为线和点
     * 默认各100个
     */
    private int mInterfrenceCount=100;
    /**
     * 声明画笔
     */
    private Paint mPaint;

    public VerificationCode(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerificationCode(Context context) {
        this(context, null);
    }

    /**
     * 获得我自定义的样式属性
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public VerificationCode(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
/**
 * 获得我们所定义的自定义样式属性
 */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.VerificationCode, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.VerificationCode_content:
                    mContent = a.getString(attr);
                    break;
                case R.styleable.VerificationCode_contentColor:
                    // 默认颜色设置为黑色
                    mContentColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.VerificationCode_contentSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mContentSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.VerificationCode_contentBackgroundColor:
                    mBackGround = a.getColor(attr, Color.WHITE);
                    break;
            }
        }
        a.recycle();
        mPaint = new Paint();
        mBound = new Rect();
        rd = new Random();

        if (mContent.equals("") || mContent == null) {//如果没有配置内容，则给一个随机数
            mContent = createRandomNumber();
        }
        this.setOnClickListener(new OnClickListener() {//设置点击事件，点击后更换随机数
            @Override
            public void onClick(View view) {
                mContent = createRandomNumber();
                postInvalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(mContentSize);
            mPaint.getTextBounds(mContent, 0, mContent.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired + mBound.width();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.setTextSize(mContentSize);
            mPaint.getTextBounds(mContent, 0, mContent.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired + mBound.height();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景
        drawBackground(canvas);
        // 绘制文本
        drawContent(canvas);
        //绘制边框
        drawBroder(canvas);
        //绘制干扰项
        drawInterference(canvas);
    }

    /**
     * 绘制背景
     */
    private void drawBackground(Canvas canvas) {
        mPaint.setColor(mBackGround);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
    }

    /**
     * 绘制边框
     */
    private void drawBroder(Canvas canvas) {
        mPaint.setColor(mBorderColor);
        mPaint.setTextSize(mBorderSize);
        canvas.drawLine(0, 0, getWidth(), 0, mPaint);
        canvas.drawLine(getWidth() - mBorderSize, 0, getWidth() - mBorderSize, getHeight(), mPaint);
        canvas.drawLine(getWidth(), getHeight() - mBorderSize, 0, getHeight() - mBorderSize, mPaint);
        canvas.drawLine(0, getHeight(), 0, 0, mPaint);
    }

    /**
     * 绘制文字内容
     */
    private void drawContent(Canvas canvas) {
        //记录随机数循环次数
        mPaint.setTextSize(mContentSize);
        mPaint.setColor(mContentColor);
        char[] numbers = mContent.toCharArray();
        int widthx = getWidth() / 4;
        for (int i = 0; i < 4; i++) {
            String number = numbers[i] + "";
            int x, y;
            mPaint.getTextBounds(number, 0, number.length(), mBound);
            x = createCoordinate(widthx, mBound,"x");
            x = x + i * widthx;
            y = createCoordinate(getHeight(), mBound,"y");
            canvas.drawText(number, x, y, mPaint);
        }
//        canvas.drawText(mContent, getWidth() / 2 - mBound.width() / 2,
//                getHeight() / 2 + mBound.height() / 2, mPaint);
    }

    /**
     * 在绘制Content的时候生成随机的x、y坐标
     * @param size 参照物尺寸
     * @param rect 用于获取文字的宽高
     * @param props 用于判断是x轴还是y轴（宽还是高）
     * @return
     */

    private int createCoordinate(int size, Rect rect,String props) {
        //记录循环次数
        int count = 0;
        //记录坐标
        int coordinate;
        do {
            coordinate = rd.nextInt(size);
            count++;
            if (count > 100) {
                try {
                    throw new Exception("设置的文字Size过大或者控件宽高度太小。导致绘制Content产生随机坐标时陷入死循环");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return coordinate;
            }
        } while (props.equals("x")?(coordinate>size-rect.width()):(coordinate<rect.height()));//欧先判断x还是y。因为x.y做的操作不一样
        return coordinate;
    }
    /**
     * 产生随机四个数的方法
     */
    private String createRandomNumber() {
        int number;
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while (i < 4) {
            int num = rd.nextInt(10);
            sb.append("" + num);
            i++;
        }
        return sb.toString();
    }

    /**
     * 随机生成干扰线的坐标数组
     *
     * @param height
     * @param width
     * @return
     */
    private int[] getLine(int height, int width) {
        int[] tempCheckNum = {0, 0, 0, 0};
        for (int i = 0; i < 4; i += 2) {
            tempCheckNum[i] = (int) (Math.random() * width);
            tempCheckNum[i + 1] = (int) (Math.random() * height);
        }
        return tempCheckNum;
    }

    /**
     * 随机生成干扰点的坐标数组
     *
     * @param height
     * @param width
     * @return
     */
    private int[] getPoint(int height, int width) {
        int[] tempCheckNum = {0, 0,};
        tempCheckNum[0] = (int) (Math.random() * width);
        tempCheckNum[1] = (int) (Math.random() * height);
        return tempCheckNum;
    }

    /**
     * 绘制干扰点和干扰线
     */
    private void drawInterference(Canvas canvas) {
        mPaint.setTextSize(1);
        mPaint.setColor(Color.BLACK);
        final int height = getHeight();
        final int width = getWidth();
        // 绘制小圆点
        int[] point;
        for (int i = 0; i < mInterfrenceCount; i++) {
            point = getPoint(height, width);
            canvas.drawCircle(point[0], point[1], 1, mPaint);
        }
        //绘制线
        int[] line;
        for (int i = 0; i < mInterfrenceCount; i++) {
            line = getLine(height, width);
            canvas.drawLine(line[0], line[1], line[2], line[3], mPaint);
        }
    }
    /**
     * 对外提供的方法。获取Content的内容
     * @return
     */
    public String getContent() {
        return mContent;
    }
}
