package com.mbobiosio.rxjavacachedretrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mbuodile Obiosio on Aug 11,2019
 * https://twitter.com/cazewonder
 * Nigeria.
 */
public class WeekResponse {

    List<WeekModel> result;
    @SerializedName("code")
    private String mCode;
    @SerializedName("message")
    private String mMessage;

    public List<WeekModel> getResult() {
        return result;
    }

    public void setResult(List<WeekModel> result) {
        this.result = result;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

}