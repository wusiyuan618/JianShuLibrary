package com.wusy.studyroad.protal.workplace.application.VoiceView.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * Created by XIAO RONG on 2018/3/20.
 */

public class RecoderFileBean implements Parcelable {
    private long time;
    private File file;

    public RecoderFileBean(long time, File file) {
        this.time = time;
        this.file = file;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.time);
        dest.writeSerializable(this.file);
    }

    protected RecoderFileBean(Parcel in) {
        this.time = in.readLong();
        this.file = (File) in.readSerializable();
    }

    public static final Creator<RecoderFileBean> CREATOR = new Creator<RecoderFileBean>() {
        @Override
        public RecoderFileBean createFromParcel(Parcel source) {
            return new RecoderFileBean(source);
        }

        @Override
        public RecoderFileBean[] newArray(int size) {
            return new RecoderFileBean[size];
        }
    };
}
