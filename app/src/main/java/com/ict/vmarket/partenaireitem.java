package com.ict.vmarket;


public class partenaireitem {

    private String mImageUrl;
    private String mName;
    private String mEmail;
    private String mDesc;
    private String mTel;
    private String mVille;
    private String mPays;
    private String mAdresse;

    public partenaireitem(){

    }

    public partenaireitem(String mImageUrl, String mName, String mEmail, String mDesc, String mTel, String mVille, String mPays, String mAdresse) {
        this.mImageUrl = mImageUrl;
        this.mName = mName;
        this.mEmail = mEmail;
        this.mDesc = mDesc;
        this.mTel = mTel;
        this.mVille = mVille;
        this.mPays = mPays;
        this.mAdresse = mAdresse;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmTel() {
        return mTel;
    }

    public void setmTel(String mTel) {
        this.mTel = mTel;
    }

    public String getmVille() {
        return mVille;
    }

    public void setmVille(String mVille) {
        this.mVille = mVille;
    }

    public String getmPays() {
        return mPays;
    }

    public void setmPays(String mPays) {
        this.mPays = mPays;
    }

    public String getmAdresse() {
        return mAdresse;
    }

    public void setmAdresse(String mAdresse) {
        this.mAdresse = mAdresse;
    }
}
