package com.android.bo.video.dreamfactory;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 * Created by Bo on 07.03.2016.
 */
public class DFUrlByChannelId implements Parcelable {

    @SerializedName("id")
    @Expose(serialize = false, deserialize = true)
    private long id;

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("is_work")
    @Expose
    private boolean isWork;
    @SerializedName("channel_id")
    @Expose(serialize = false, deserialize = true)
    private long channelId;

    public DFUrlByChannelId(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The isWork
     */
    public boolean isWork() {
        return isWork;
    }

    /**
     * @param isWork The is_work
     */
    public void setIsWork(boolean isWork) {
        this.isWork = isWork;
    }

    /**
     * @return The channelId
     */
    public long getChannelId() {
        return channelId;
    }

    /**
     * @param channelId The channel_id
     */
    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.url);
        dest.writeByte(isWork ? (byte) 1 : (byte) 0);
        dest.writeLong(this.channelId);
    }

    protected DFUrlByChannelId(Parcel in) {
        this.id = in.readLong();
        this.url = in.readString();
        this.isWork = in.readByte() != 0;
        this.channelId = in.readLong();
    }

    public static final Parcelable.Creator<DFUrlByChannelId> CREATOR = new Parcelable.Creator<DFUrlByChannelId>() {
        public DFUrlByChannelId createFromParcel(Parcel source) {
            return new DFUrlByChannelId(source);
        }

        public DFUrlByChannelId[] newArray(int size) {
            return new DFUrlByChannelId[size];
        }
    };
}
