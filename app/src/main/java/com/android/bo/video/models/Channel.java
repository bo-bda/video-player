package com.android.bo.video.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.bo.video.R;

import java.util.ArrayList;
import java.util.HashSet;

/*
 * Created by Bo on 21.11.2015.
 */
public class Channel implements Parcelable {

    private String name;
    private HashSet<String> uri = new HashSet<>();
    private int icon;

    public Channel(String name, String uri, int icon) {
        this.name = name;
        this.uri.add(uri);
        this.icon = icon;
    }

    public Channel(String name, String uri) {
        this.name = name;
        this.uri.add(uri);
        this.icon = R.mipmap.ic_launcher;
    }

    public void addUri(ArrayList<String> uri) {
        this.uri.addAll(uri);
    }

    public String getName() {
        return name;
    }

    public HashSet<String> getUri() {
        return uri;
    }

    public int getIcon() {
        return icon;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeSerializable(this.uri);
        dest.writeInt(this.icon);
    }

    protected Channel(Parcel in) {
        this.name = in.readString();
        this.uri = (HashSet<String>) in.readSerializable();
        this.icon = in.readInt();
    }

    public static final Parcelable.Creator<Channel> CREATOR = new Parcelable.Creator<Channel>() {
        public Channel createFromParcel(Parcel source) {
            return new Channel(source);
        }

        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        return o instanceof Channel && ((Channel) o).name.equalsIgnoreCase(name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
