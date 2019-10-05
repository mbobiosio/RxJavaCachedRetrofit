package com.mbobiosio.rxjavacachedretrofit.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Mbuodile Obiosio on Aug 14,2019
 * https://twitter.com/cazewonder
 * Nigeria.
 */
@Root(name = "rss", strict = false)
public class DailyModel {
    @Element(name = "channel")
    private Channel channel;
    @Attribute
    private String version;

    public Channel getChannel() {
        return channel;
    }
    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}