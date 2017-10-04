package com.turnup.posts;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Location 
{
	private String name;
	private String address;
	private String surburb;
	private String city;
	private String latitude;
	private String longitude;
	private String locationIcon;
	
	
	public Location(String name,String address,String surburb,String city,String latitude,String longitude,String locationImage) 
	{
		this.name = name;
		this.address = address;
		this.surburb = surburb;
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
		this.locationIcon = locationImage;
	}
	
	public Location()
	{
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSurburb() {
		return surburb;
	}
	public void setSurburb(String surburb) {
		this.surburb = surburb;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLocationIcon() {
		return locationIcon;
	}

	public void setLocationIcon(String locationIcon) {
		this.locationIcon = locationIcon;
	}
	
	
}
