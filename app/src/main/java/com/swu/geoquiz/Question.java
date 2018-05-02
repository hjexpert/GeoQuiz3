package com.swu.geoquiz;

/**
 * Created by HyungJong on 2018-02-18.
 */

public class Question {
    private int mTextResID;
    private boolean mAnswerTrue;
    private boolean mIsSloved;
    private boolean mTried;

    public Question(int textResId, boolean answerTrue) {
        mTextResID = textResId;
        mAnswerTrue = answerTrue;
        mIsSloved =  false;
        mTried = false;
    }

    public int getTextResID() {
        return mTextResID;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setTextResID(int textResID) {
        mTextResID = textResID;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public void Sloved() {
        mIsSloved = true;
    }

    public boolean isSloved() {
        return mIsSloved;
    }

    public void mTried() {
         mTried = true;
    }

}
