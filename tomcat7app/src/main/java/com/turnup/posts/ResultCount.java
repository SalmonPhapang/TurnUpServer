package com.turnup.posts;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultCount {
	 private int count;
	    public int getImagedata() {
		return count;
	}
	public void setImagedata(int imagedata) {
		this.count = imagedata;
	}
		public ResultCount(int count)
	    {
	    	super();
	    	this.count = count;
	    }
	    public ResultCount()
	    {
	    	
	    }
	    
}
