package com.mbobiosio.rxjavacachedretrofit.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;

import org.parceler.ParcelConstructor;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Mbuodile Obiosio on Aug 14,2019
 * https://twitter.com/cazewonder
 * Nigeria.
 */
@org.parceler.Parcel
@Root(name = "item", strict = false)
public class Item implements Parcelable {

    @Element(name = "pubDate")
    String pubDate;

    @Element(name = "title")
    String title;

    @Element(name = "description")
    String description;

    @Element(name = "link")
    String link;

    @Element(name = "guid")
    String guid;

    @ParcelConstructor
    public Item(@Element(name = "title") String title,
                @Element(name = "description") String description,
                @Element(name = "pubDate") String pubDate,
                @Element(name = "link") String link,
                @Element(name = "guid") String guid) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
        this.guid = guid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public Item(Parcel in) {
        title = in.readString();
        description = in.readString();
        pubDate = in.readString();
        link = in.readString();
        guid = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(pubDate);
        dest.writeString(link);
        dest.writeString(guid);
    }

    public String getDescriptionPlaintext() {
        // https://stackoverflow.com/a/10581020
        return Html.fromHtml(description).toString().replace('\n', (char) 32)
                .replace((char) 160, (char) 32).replace((char) 65532, (char) 32).trim();
    }

}