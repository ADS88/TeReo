package com.android.example.tereomaori;

/**
 * Created by Andrew on 7/07/2019.
 */

public class Word {

    private String mEnglishTranslation;
    private String mMaoriTranslation;
    private int mImageResourceId;
    private boolean mHasImage;
    private int mSoundFileId;

    public Word(String englishTranslation, String maoriTranslation){
        mEnglishTranslation = englishTranslation;
        mMaoriTranslation = maoriTranslation;
        mHasImage = false;
    }

    public Word(String englishTranslation, String maoriTranslation, int imageResourceId, int soundFileId){
        mEnglishTranslation = englishTranslation;
        mMaoriTranslation = maoriTranslation;
        mImageResourceId = imageResourceId;
        mHasImage = true;
        mSoundFileId = soundFileId;
    }

    public Word(String englishTranslation, String maoriTranslation, int soundFileId){
        mEnglishTranslation = englishTranslation;
        mMaoriTranslation = maoriTranslation;
        mSoundFileId = soundFileId;
        mHasImage = false;
    }


    public String getEnglishTranslation(){
        return mEnglishTranslation;
    }

    public String getMaoriTranslation(){
        return mMaoriTranslation;
    }

    public int getImageResourceId() { return mImageResourceId; }

    public boolean hasImage(){ return mHasImage; }

    public int getSoundFileId() { return mSoundFileId; }
}
