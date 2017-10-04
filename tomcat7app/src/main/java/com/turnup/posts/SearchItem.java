package com.turnup.posts;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Administrator on 2016/05/11.
 */

@XmlRootElement
public class SearchItem
{
    private String name;
    private String imageUrl;
    private String location;
    public SearchItem()
    {

    }
    public SearchItem(String name,String imageurl,String location)
    {
    	this.name = name;
    	this.imageUrl = imageurl;
    	this.location = location;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public String getLocation() {
		return location;
	}
    public void setLocation(String location) {
		this.location = location;
	}
}
