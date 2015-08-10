package com.michail.messengerclient;

/**
 * Created by mmalykov on 8/10/15.
 */
public class CountryCodes {

    private String mPhoneCode;
    private String mCountryCode;

    public CountryCodes(String mPhoneCode, String mCountryCode) {
        this.mPhoneCode = mPhoneCode;
        this.mCountryCode = mCountryCode;
    }

    public void setPhoneCode(String mPhoneCode) {
        this.mPhoneCode = mPhoneCode;
    }

    public String getPhoneCode() {
        return mPhoneCode;
    }

    public void setCountryCode(String mCountryCode) {
        this.mCountryCode = mCountryCode;
    }

    public String getCountryCode() {
        return mCountryCode;
    }
}

