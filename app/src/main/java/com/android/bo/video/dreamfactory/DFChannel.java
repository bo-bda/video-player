package com.android.bo.video.dreamfactory;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Bo on 07.03.2016.
 */
public class DFChannel implements Parcelable {

    private long id;
    @SerializedName("channel_name")
    @Expose
    private String channelName;
    @SerializedName("channel_country")
    @Expose
    private String channelCountry;
    @SerializedName("url_by_channel_id")
    @Expose
    private List<DFUrlByChannelId> urlByChannelId = new ArrayList<>();

    public DFChannel(String channelName, String channelCountry) {
        this.channelName = channelName;
        this.channelCountry = channelCountry;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return The channelName
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * @param channelName The channel_name
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * @return The channelCountry
     */
    public String getChannelCountry() {
        return channelCountry;
    }

    /**
     * @param channelCountry The channel_country
     */
    public void setChannelCountry(String channelCountry) {
        this.channelCountry = channelCountry;
    }

    /**
     * @return The urlByChannelId
     */
    public List<DFUrlByChannelId> getUrlByChannelId() {
        return urlByChannelId;
    }

    /**
     * @param urlByChannelId The url_by_channel_id
     */
    public void setUrlByChannelId(List<DFUrlByChannelId> urlByChannelId) {
        this.urlByChannelId = urlByChannelId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.channelName);
        dest.writeString(this.channelCountry);
        dest.writeTypedList(this.urlByChannelId);
    }

    protected DFChannel(Parcel in) {
        this.id = in.readLong();
        this.channelName = in.readString();
        this.channelCountry = in.readString();
        this.urlByChannelId = new ArrayList<>();
        in.readTypedList(this.urlByChannelId, DFUrlByChannelId.CREATOR);
    }

    public static final Parcelable.Creator<DFChannel> CREATOR = new Parcelable.Creator<DFChannel>() {
        public DFChannel createFromParcel(Parcel source) {
            return new DFChannel(source);
        }

        public DFChannel[] newArray(int size) {
            return new DFChannel[size];
        }
    };
}
