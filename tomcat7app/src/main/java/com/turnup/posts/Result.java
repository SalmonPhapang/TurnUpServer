package com.turnup.posts;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Result {
	 private int count;
	    public Result(int data)
	    {
	    	super();
	    	this.count = data;
	    }
	    public Result()
	    {
	    	
	    }
	    public int getCount() {
			return count;
		}
	    public void setCount(int count) {
			this.count = count;
		}
}
