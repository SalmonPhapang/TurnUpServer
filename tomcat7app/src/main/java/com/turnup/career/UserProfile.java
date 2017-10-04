package com.turnup.career;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserProfile
{
	private int id;
	private  String name;
	private String shots;
	private String bottles;
	private String level;
	private  String bio;
		private  String email;
		private  String cell;
		private String username;
		private String imageurl;
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


    public String getShots() {
        return shots;
    }

    public void setShots(String shots) {
        this.shots = shots;
    }

    public String getBottles() {
        return bottles;
    }

    public void setBottles(String bottles) {
        this.bottles = bottles;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

   public UserProfile()
    {

    }
   public UserProfile(int userid,String username,String name,String bio,String email,String cell,String shots,String bottles,String level,String imageurl)
   {
	   this.id = userid;
	   this.bottles = bottles;
	   this.shots = shots;
	   this.level = level;
	   this.username = username;
	   this.bio = bio;
	   this.email = email;
	   this.cell = cell;
	   this.name = name;
	   this.imageurl = imageurl;
	  
   }
}
