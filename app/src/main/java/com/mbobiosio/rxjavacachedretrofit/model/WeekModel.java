package com.mbobiosio.rxjavacachedretrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by Mbuodile Obiosio on Aug 11,2019
 * https://twitter.com/cazewonder
 * Nigeria.
 */
public class WeekModel extends WeekResponse implements Serializable {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("count")
    @Expose
    public String count;
    @SerializedName("topic")
    @Expose
    public String topic;
    @SerializedName("verse")
    @Expose
    public String verse;
    @SerializedName("text")
    @Expose
    public String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}