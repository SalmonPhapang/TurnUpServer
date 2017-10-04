
package com.turnup.attended;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Administrator on 2016/04/28.
 */
@XmlRootElement
public class AttendedItem
{
    private String name;
    private String eventSub;
    private String date;
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public AttendedItem(String name,String date,String imageurl,String sub)
    {
        this.setName(name);
        this.setEventSub(sub);
        this.setDate(date);
        this.setImageUrl(imageurl);
    }
    public AttendedItem(){}

    public String getEventSub() {
        return eventSub;
    }

    public void setEventSub(String eventSub) {
        this.eventSub = eventSub;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

