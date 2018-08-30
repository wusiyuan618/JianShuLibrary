package minto.com.createview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import minto.com.createview.R;

/**
 * Created by Administrator on 2017/3/16.
 */
public class MyImageView extends View {
    /**
     * 图片
     */
    private Bitmap mImage;
    /**
     * 图片位置
     */
    private int mImageLocatioin = 0;
    private final int IMAGE_LOCATION_FILLXY = 0;
    private final int IMAGE_LOCATION_CENTER = 1;
    /**
     * 文字内容
     */
    private String mTitle = "";
    /**
     * 文字大小
     */
    private int mTitleSize = 15;
    /**
     * 文字颜色
     */
    private int mTitleColor = Color.BLACK;
    /**
     * 边框颜色
     */
    private int mBorderColor = Color.RED;
    /**
     * 边框宽度
     */
    private int mBorderWidth = 5;
    private Paint mPaint;
    private Rect mRect;
    private Rect mBitMapRect;

    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyImageView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.MyImageView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.MyImageView_imageLocation:
                    mImageLocatioin = a.getInt(attr,0);
                    break;
                case R.styleable.MyImageView_title:
                    mTitle = a.getString(attr);
                    break;
                case R.styleable.MyImageView_titleSize:
                    mTitleSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            15, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.MyImageView_titleColor:
                    mTitleColor = a.getColor(attr, Color.BLACK);
            }
        }
        a.recycle();
        mPaint = new Paint();
        mRect = new Rect();
        mBitMapRect=new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//宽度模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//控件宽度
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//高度模式
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);//控件高度
        int width = 0, height = 0;
        int padingWidth = getPaddingLeft() + getPaddingRight();
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.getTextBounds(mTitle, 0, mTitle.length(), mRect);
            width = Math.min(widthSize, Math.max(mImage.getWidth(), mRect.width()) + padingWidth + mBorderWidth * 2);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int temp = mImage.getHeight() + mRect.height() + getPaddingBottom() + getPaddingTop() + mBorderWidth * 2;
            height = Math.min(temp, heightSize);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBorder(canvas);
        drawImage(canvas);
        drawTitle(canvas);

    }

    /**
     * 画边框
     */
    public void drawBorder(Canvas canvas) {
        mPaint.reset();
        mBorderWidth = 20;
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mBorderColor);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
    }

    /**
     * 画文字
     */
    public void drawTitle(Canvas canvas) {
        float x,y;
        mRect.left = getPaddingLeft();
        mRect.right = getWidth() - getPaddingRight();
        mRect.top = getPaddingTop();
        mRect.bottom = getHeight() - getPaddingBottom();
        mPaint.reset();
        mPaint.setTextSize(mTitleSize);
        mPaint.setColor(mTitleColor);
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mRect);
        if (mRect.width() > getWidth()) {//如果文字内容过长，则截取内容，将超出部分以“。。。”表示
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitle, paint, (float) getWidth() -mBorderWidth- getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg,mBorderWidth, getHeight() -mBorderWidth- getPaddingBottom(), mPaint);
        } else {//正常情况下，文字居中。
           x=(getWidth()-mRect.width()*1.0f)/2;
            y=getHeight()-mBorderWidth-getPaddingBottom();
            canvas.drawText(mTitle, x,y, mPaint);
        }
    }

    /**
     * 画图片
     */
    public void drawImage(Canvas canvas) {
        mPaint.reset();
        mPaint.setTextSize(mTitleSize);
        mPaint.getTextBounds(mTitle,0,mTitle.length(),mRect);
        if (mImageLocatioin == IMAGE_LOCATION_FILLXY) {
            mBitMapRect.left=mBorderWidth+getPaddingLeft();
            mBitMapRect.top=mBorderWidth+getPaddingTop();
            mBitMapRect.right=getWidth()-mBorderWidth-getPaddingRight();
            mBitMapRect.bottom=getHeight()-mBorderWidth-mRect.height()-getPaddingBottom();
            canvas.drawBitmap(mImage, null, mBitMapRect, mPaint);
        }
        if (mImageLocatioin == IMAGE_LOCATION_CENTER) {
            if(mImage.getWidth()>(getWidth()-2*mBorderWidth)){
                mBitMapRect.left=mBorderWidth;
                mBitMapRect.right=getWidth()-mBorderWidth;
            }else {
                mBitMapRect.left = (getWidth() - mImage.getWidth()) / 2;
                mBitMapRect.right = (getWidth() + mImage.getWidth() + getPaddingLeft()) / 2;
            }
            if(mImage.getHeight()>(getHeight()-mRect.height()-2*mBorderWidth)){
                mBitMapRect.top=mBorderWidth+getPaddingTop();
                mBitMapRect.bottom=getHeight()-mBorderWidth-mRect.height()-getPaddingBottom();
            }else {
                mBitMapRect.top = (getHeight() - mRect.height() - mImage.getHeight()) / 2;
                mBitMapRect.bottom = (getHeight() - mRect.height() + mImage.getHeight() - getPaddingTop()) / 2;
            }
            canvas.drawBitmap(mImage, null, mBitMapRect, mPaint);
        }
    }
}