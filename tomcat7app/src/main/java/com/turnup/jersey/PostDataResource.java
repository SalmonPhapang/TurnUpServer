package com.turnup.jersey;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.turnup.attended.AttendedItem;
import com.turnup.career.UserProfile;
import com.turnup.posts.EventItem;
import com.turnup.posts.FeedItem;
import com.turnup.posts.FeedItemDetails;
import com.turnup.posts.Location;
import com.turnup.posts.Result;
import com.turnup.posts.SearchItem;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONObject;
import java.util.Properties;

@Path("/TimeLine")
public class PostDataResource 
{
	@GET
	@Path("/getTimeLine")
	@Produces(MediaType.APPLICATION_JSON)
	public List<FeedItem> getPosts()
	{
		List<FeedItem> data = null;
		try {
			data =  DatabaseHandler.getPosts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	@GET
	@Path("/getEventTimeLine")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EventItem> getEventPosts()
	{
		List<EventItem> data = null;
		try {
			data =  DatabaseHandler.getEventPost();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	@GET
	@Path("/getTimeLineCount")
	@Produces(MediaType.APPLICATION_JSON)
	public Result getPostsCount()
	{
		List<FeedItem> data = null;
		try {
			data =  DatabaseHandler.getPosts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count = data.size();
		return new Result(count);
	}
	@GET
	@Path("/insertNewFeed")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean insertNewFeed(@QueryParam("imageLocation") String imageLocation,@QueryParam("url") String thumbnail,@QueryParam("name") String name,@QueryParam("location") String location,@QueryParam("profilepic") String profilepic,@QueryParam("shots") int shots,@QueryParam("status") String status,@QueryParam("isVideo") boolean isVideo)
	{
		boolean result = false;
		try {
			result =  DatabaseHandler.insertNewFeed(imageLocation,thumbnail ,name, location, profilepic, shots, status, isVideo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return result;
	}
	@POST
	@Path("/loadMoreFeeds")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<FeedItem> LoadMoreFeeds(String request)
	{
		List<FeedItem> data = null;
		try {
			JSONObject obj = new JSONObject(request);
			System.out.println("Recieved request form client");
			String index = obj.getString("index");
			data =  DatabaseHandler.LoadMoreFeeds(index);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		
		return data;
	}
	@POST
	@Path("/loadMoreEventFeeds")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<EventItem> LoadMoreEventFeeds(String request)
	{
		List<EventItem> data = null;
		try {
			JSONObject obj = new JSONObject(request);
			System.out.println("Recieved request form client");
			String index = obj.getString("index");
			data =  DatabaseHandler.LoadMoreEventFeeds(index);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		
		return data;
	}
	@POST
	@Path("/getFeedItemDetails")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<FeedItemDetails> getFeedItemDetails(String request)
	{
		List<FeedItemDetails> data = null;
		try {
			JSONObject obj = new JSONObject(request);
			String index = obj.getString("index");
			data =  DatabaseHandler.getFeedDetails(index);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return data;
	}
	@GET
	@Path("/updateShots")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateShots(@QueryParam("feedId") String id, @QueryParam("like_status") String status)
	{
		boolean success = false;
		try {
			success =  DatabaseHandler.updateShots(id,status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return String.valueOf(success);
	}
	@GET
	@Path("/UpdateClientPassword")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean updateClientPassword(@QueryParam("name") String name, @QueryParam("password") String pwd)
	{
		Boolean data = null;
		try {
			System.out.println("updating client password");
			data =  DatabaseHandler.updateClientUserPassword(name, pwd);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		return data;
	}
	@GET
	@Path("/UpdateUserPassword")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean updateUserPassword(@QueryParam("name") String name, @QueryParam("password") String pwd,@QueryParam("password") String currentpwd)
	{
		Boolean data = null;
		try {
			System.out.println("updating user password");
			data =  DatabaseHandler.updateUserPassword(name, pwd,currentpwd);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		return data;
	}
	@GET
	@Path("/getClientProfile")
	@Produces(MediaType.APPLICATION_JSON)
	public  Map<String,String> getClientprofile(@QueryParam("username") String uname)
	{
		 Map<String,String> data = null;
		try {
			System.out.println(new Date()+" Fetching Profile Details for client:"+uname);
			data =  DatabaseHandler.getClientProfile(uname);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return data;
	}
	@GET
	@Path("/getProfile")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserProfile> getPosts(@QueryParam("username") String uname)
	{
		List<UserProfile> data = null;
		try {
			System.out.println(new Date()+" Fetching Profile Details for user:"+uname);
			data =  DatabaseHandler.getProfile(uname);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return data;
	}
	
	@POST
	@Path("/getAttendedClub")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AttendedItem>  getAttendedClubs(String request)
	{
		List<AttendedItem> data = null;
		try {
			JSONObject obj = new JSONObject(request);
			String id = obj.getString("id");
			System.out.println(new Date()+" fetching attended list for userid: "+id);
			data = DatabaseHandler.getAttended(Integer.parseInt(id));
		
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return data;
	}

	@GET
	@Path("/updateProfile")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateProfile(@QueryParam("id") int id,@QueryParam("name") String name ,@QueryParam("username") String uname,@QueryParam("bio") String bio,@QueryParam("email") String email,@QueryParam("cell") String cell,@QueryParam("imageurl") String image)
	{
		boolean success = false;
		try {
			System.out.println(new Date()+" Updating Profile Details for user: "+uname);
			success =  DatabaseHandler.updateUser(id, name, uname, bio, email, cell,image);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return String.valueOf(success);
	}
	
	@GET
	@Path("/updateProfileShots")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateProfile(@QueryParam("username") String uname,@QueryParam("password") String password,@QueryParam("amount") int amount,@QueryParam("ufdn") int ufdn,@QueryParam("userid") int userid)
	{
		boolean success = false;
		try {
			System.out.println(new Date()+" Updating Profile Shots for user: "+uname);
			success =  DatabaseHandler.updateUserShots(uname,password,amount,ufdn,userid);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return String.valueOf(success);
	}
	
	@GET
	@Path("/getClubs")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SearchItem> getClubs()
	{
		List<SearchItem> data = null;
		try {
			System.out.println(new Date()+" Fetching Clubs list...");
			data =  DatabaseHandler.getClubs();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return data;
	}
	
	
	@GET
	@Path("/getEvents")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SearchItem> getEvents()
	{
		List<SearchItem> data = null;
		try {
			System.out.println(new Date()+"Fetching Events list...");
			data =  DatabaseHandler.getEvents();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return data;
	}
	
	@POST
	@Path("/getSearchItem")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<FeedItem> getItem(String request)
	{
		List<FeedItem> data = null;
		try {
			JSONObject obj = new JSONObject(request);
			System.out.println(obj);
			String name = obj.getString("name");
			String dbname = obj.getString("dbname");
			System.out.println(new Date()+"Fetching Search list for :"+name+"....");
			data =  DatabaseHandler.getSearchItem(name,dbname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	@GET
	@Path("/getCity")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getCity()
	{
		List<String> data = null;
		try {
			System.out.println(new Date()+"Fetching City list...");
			data =  DatabaseHandler.getCity();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return data;
	}
	
	@GET
	@Path("/getFeedLocation")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getFeedLocation()
	{
		List<String> data = null;
		try {
			System.out.println(new Date()+"Fetching City list...");
			data =  DatabaseHandler.getCity();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return data;
	}
	@GET
	@Path("/getMapLocation")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Location> getMapLocation()
	{
		List<Location> data = null;
		try {
			System.out.println(new Date()+"Fetching City list...");
			data =  DatabaseHandler.getMaplocation();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return data;
	}
	
}
