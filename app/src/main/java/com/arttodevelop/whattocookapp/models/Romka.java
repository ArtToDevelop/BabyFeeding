package com.arttodevelop.whattocookapp.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Romka implements Parcelable {
    public static final String TableName = "CITY";

    private String serverId;
    private Integer id;
    private String name;

    public Romka()
    {

    }

    public Romka(String serverId, String name)
    {
        this.serverId = serverId;
        this.name = name;
    }

    private Romka(Parcel in) {
        serverId = in.readString();
        id = in.readInt();
        name = in.readString();
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(serverId);
        parcel.writeInt(id);
        parcel.writeString(name);
    }

    public static final Parcelable.Creator<Romka> CREATOR = new Parcelable.Creator<Romka>() {
        public Romka createFromParcel(Parcel in) {
            return new Romka(in);
        }

        public Romka[] newArray(int size) {
            return new Romka[size];
        }
    };
}
