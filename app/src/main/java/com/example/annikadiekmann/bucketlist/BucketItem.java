package com.example.annikadiekmann.bucketlist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "bucketList")

public class BucketItem implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "done")
    private Boolean mDone;

    public BucketItem(String mTitle, String mDescription, Boolean mDone) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDone = mDone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() { return mDescription; }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Boolean getDone() {
        return mDone;
    }

    public boolean isChecked() { return mDone; }

    public void setDone(Boolean mDone) {
        this.mDone = mDone;
    }


    @Override
    public int describeContents() {
        return 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
        dest.writeValue(this.mDone);
    }

    protected BucketItem(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        this.mDone = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<BucketItem> CREATOR = new Creator<BucketItem>() {
        @Override
        public BucketItem createFromParcel(Parcel source) {
            return new BucketItem(source);
        }

        @Override
        public BucketItem[] newArray(int size) {
            return new BucketItem[size];
        }
    };
}

