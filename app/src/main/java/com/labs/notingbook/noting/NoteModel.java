package com.labs.notingbook.noting;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Drummer on 28.02.2017.
 */

public class NoteModel implements Parcelable {

    private String mName;
    private String mDescription;
    private String mText;
    private String mImagePath;
    private Date mCreationTime;
    private ImportanceLevel mImportance;

    private NoteModel() {}

    protected NoteModel(Parcel in) {
        mName = in.readString();
        mDescription = in.readString();
        mText = in.readString();
        mImagePath = in.readString();
    }

    public static final Creator<NoteModel> CREATOR = new Creator<NoteModel>() {
        @Override
        public NoteModel createFromParcel(Parcel in) {
            return new NoteModel(in);
        }

        @Override
        public NoteModel[] newArray(int size) {
            return new NoteModel[size];
        }
    };

    public ImportanceLevel getImportance() {
        return mImportance;
    }

    public void setImportance(ImportanceLevel mImportance) {
        this.mImportance = mImportance;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String mThumbnailPath) {
        this.mImagePath = mThumbnailPath;
    }

    public Date getCreationTime() {
        return mCreationTime;
    }

    public void setCreationTime(Date mCreationTime) {
        this.mCreationTime = mCreationTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(mName);
        parcel.writeString(mDescription);
        parcel.writeString(mText);
        parcel.writeString(mImagePath);
    }

    public static class Builder {
        private String mName;
        private String mDescription;
        private String mText;
        private String mImagePath;
        private Date mCreationTime;
        private ImportanceLevel mImportanceLevel;

        public Builder withName(String name)
        {
            mName = name;
            return this;
        }

        public Builder withDescription(String description)
        {
            mDescription = description;
            return this;
        }

        public Builder withText(String text)
        {
            mText = text;
            return this;
        }

        public Builder withDate(Date date)
        {
            mCreationTime = date;
            return this;
        }

        public Builder withImagePath(String path)
        {
            mImagePath = path;
            return this;
        }

        public Builder withImportanceLevel(ImportanceLevel level)
        {
            mImportanceLevel = level;
            return this;
        }

        public NoteModel build()
        {
            NoteModel note = new NoteModel();
            note.mName = mName;
            note.mDescription = mDescription;
            note.mText = mText;
            note.mCreationTime = mCreationTime;
            note.mImagePath = mImagePath;
            note.mImportance = mImportanceLevel;
            return note;
        }
    }

    public enum ImportanceLevel {
        One(1),
        Two(2),
        Three(3);

        private int level;

        private static ImportanceLevel defaultLevel = One;

        ImportanceLevel(int level) {
            this.level = level;
        }

        public int getLevel()
        {
            return this.level;
        }

        public static ImportanceLevel createFromInt(int level)
        {
            for (ImportanceLevel importanceLevel : values()) {
                if(importanceLevel.level == level) return importanceLevel;
            }
            return defaultLevel;
        }
    }
}
