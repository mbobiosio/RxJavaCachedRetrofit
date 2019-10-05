package com.mbobiosio.rxjavacachedretrofit.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by Mbuodile Obiosio on Aug 14,2019
 * https://twitter.com/cazewonder
 * Nigeria.
 */
@Root(name = "channel", strict =false)
public class Channel {

    @ElementList(name = "item", inline = true)
    public ArrayList<Item> mItems;

    @Element(required = false)
    private String title;
    @Element(required = false)
    private String description;
    @Element(required = false)
    private String link;
    @Element(required = false)
    private String generator;
    @Element(required = false)
    private String lastBuildDate;
    @Element(required = false)
    private String copyright;
    @Element(required = false)
    private String language;
    @Element(required = false)
    private String ttl;

    public ArrayList<Item> getItems(){
        return mItems;
    }

    public void setItems(ArrayList<Item> items) {
        mItems = items;
    }

}