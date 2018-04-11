package com.bignerdranch.android.criminalintentlesson;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private boolean mRequiresPolice;
    private Date mTime;

    public Crime() {
        setId(UUID.randomUUID());
        mDate = new Date();
        mTime = new Date();
    }

    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
        mTime = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public boolean requiresPolice() {
        return mRequiresPolice;
    }

    public void setRequiresPolice(boolean mRequiresPolice) {
        this.mRequiresPolice = mRequiresPolice;
    }

    public Date getTime() { return mTime; }

    public void setTime(Date mTime) { this.mTime = mTime; }

    @Override
    public boolean equals(Object c) {
        if(this == c)
            return true;
        if(!(c instanceof Crime))
            return false;
        Crime cr = (Crime)c;
        return this.getId().equals(cr.getId());
    }
}
