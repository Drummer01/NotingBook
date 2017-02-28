package com.labs.notingbook.noting;

import java.util.Date;

/**
 * Created by Drummer on 28.02.2017.
 */

public class NoteModel {

    private String mName;
    private String mDescription;
    private String mText;
    private String mImagePath;
    private Date mCreationTime;
    private ImportanceLevel mImportance;

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
