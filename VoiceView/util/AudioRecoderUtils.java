package com.wusy.studyroad.protal.workplace.application.VoiceView.util;

import java.io.IOException;
import java.util.Calendar;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

/**
 * Created by MarioStudio on 2016/5/12.
 */

public class AudioRecoderUtils {
    public static AudioRecoderUtils utils;
    private AudioRecoderUtils() {

    }
    public synchronized static AudioRecoderUtils getInstance(){
        if(utils==null){
            utils=new AudioRecoderUtils();
        }
        return utils;
    }
    private MediaRecorder mMediaRecorder;
    private MediaPlayer mMediaPlayer;
    private boolean isPlayer=false;
    private final String TAG = "MediaRecord";
    public static final int MAX_LENGTH = 1000 * 60 * 10;// 最大录音时长1000*60*10;

    private OnAudioStatusUpdateListener audioStatusUpdateListener;
    private OnMediaPlayerComplationListen mediaPlayerComplationListen;
    private long startTime;
    private long endTime;
    private String currentFilePath="";

    /**
     * 开始录音 使用amr格式
     * 录音文件
     *
     * @return
     */
    public void startRecord(String filePath) {
        // 开始录音
        /* ①Initial：实例化MediaRecorder对象 */
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();
        try {
            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            /* ③准备 */
            currentFilePath=filePath;
            mMediaRecorder.setOutputFile(filePath);
            mMediaRecorder.setMaxDuration(MAX_LENGTH);
            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
            // AudioRecord audioRecord.
            /* 获取开始时间* */
            startTime = System.currentTimeMillis();
            updateMicStatus();
            Log.i("ACTION_START", "startTime" + startTime+",downPath"+filePath);
        } catch (IllegalStateException e) {
            Log.i(TAG, "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        } catch (IOException e) {
            Log.i(TAG, "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        }
    }

    /**
     * 停止录音
     */
    public long stopRecord() {
        try {
            if (mMediaRecorder == null)
                return 0L;
            endTime = System.currentTimeMillis();
            Log.i("ACTION_END", "endTime" + endTime);
            mMediaRecorder.stop();
            initMediaRecorder();
            return endTime - startTime;
        }catch (Exception e){
          initMediaRecorder();
            return 0;
        }
    }

    public void initMediaRecorder(){
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    private final Handler mHandler = new Handler();

    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };

    /**
     * 更新话筒状态
     */
    private int BASE = 1;
    private int SPACE = 100;// 间隔取样时间

    public void setOnAudioStatusUpdateListener(OnAudioStatusUpdateListener audioStatusUpdateListener) {
        this.audioStatusUpdateListener = audioStatusUpdateListener;
    }

    private void updateMicStatus() {
        if (mMediaRecorder != null) {
            double ratio = (double) mMediaRecorder.getMaxAmplitude() / BASE;
            double db = 0;// 分贝
            if (ratio > 1) {
                db = 20 * Math.log10(ratio);
                if (null != audioStatusUpdateListener) {
                    audioStatusUpdateListener.onUpdate(db);
                }
            }
            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
        }
    }

    public interface OnAudioStatusUpdateListener {
        public void onUpdate(double db);
    }
    public interface OnMediaPlayerComplationListen{
        public void onCompletion();
    }

    public void setMediaPlayerComplationListen(OnMediaPlayerComplationListen mediaPlayerComplationListen) {
        this.mediaPlayerComplationListen = mediaPlayerComplationListen;
    }

    public void playerStart(String filePath) {
        try {
            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();
            }
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(filePath);
            mMediaPlayer.prepare();
            playerCompletion();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
        isPlayer=true;
    }

    public void playerCompletion(){
        if(mMediaPlayer==null)return;
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlayer=false;
                if (mediaPlayerComplationListen!=null) mediaPlayerComplationListen.onCompletion();
            }
        });
    }

    public boolean playerStop() {
        if (mMediaPlayer == null) return false;
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
        isPlayer=false;
        return true;
    }
    public String getFilePath(){
        Calendar c=Calendar.getInstance();
        String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/语音"+c.get(Calendar.HOUR_OF_DAY)+c.get(Calendar.MINUTE)+c.get(Calendar.SECOND) +".amr";
        Log.i("msg",path);
        return path;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public boolean isPlayer() {
        return isPlayer;
    }
}
