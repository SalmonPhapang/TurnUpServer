package com.turnup.jersey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.turnup.attended.AttendedItem;
import com.turnup.career.UserProfile;
import com.turnup.posts.EventItem;
import com.turnup.posts.FeedItem;
import com.turnup.posts.FeedItemDetails;
import com.turnup.posts.Location;
import com.turnup.posts.SearchItem;
public class DatabaseHandler 
{
	 @SuppressWarnings("finally")
	    public static Connection createConnection() throws Exception {
	        Connection con = null;
	        try {
	            Class.forName(Constants.dbClass);
	            con = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPwd);
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            return con;
	        }
	    }
	    /**
	     * Method to check whether uname and pwd combination are correct
	     * 
	     * @param uname
	     * @param pwd
	     * @return
	     * @throws Exception
	     */
	    public static Map<String, String> checkLogin(String uname, String pwd) throws Exception {
	        boolean isUserAvailable = false;
	        Connection dbConn = null;
	        Map<String, String> results = new HashMap<>();
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	           // String password = PasswordStorage.createHash(pwd);
	            Statement stmt = dbConn.createStatement();
	            String query = "";
	            if(uname.contains("CLI_"))
	            {
	            	String username = uname.substring(4);
	            	System.out.println(username);
	            	query = "SELECT password FROM client WHERE name='"+username+"'";
	            	results.put("type", "client");
	            }
	            else
	            {
	             query = "SELECT password FROM user WHERE username = '" + uname+"'";
	             results.put("type", "user");
	            }
	            //System.out.println(query);
	            ResultSet rs = stmt.executeQuery(query);
	            while (rs.next()) {
	            	boolean status = PasswordStorage.verifyPassword(pwd, rs.getString("password"));
	                isUserAvailable = true;
	                results.put("status", String.valueOf(status));
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        //results.put("status", String.valueOf(isUserAvailable));
	        return results;
	    }
	    /**
	     * Method to insert uname and pwd in DB
	     * 
	     * @param name
	     * @param uname
	     * @param pwd
	     * @return
	     * @throws SQLException
	     * @throws Exception
	     */
	    public static boolean insertUser(String name, String email, String pwd) throws SQLException, Exception {
	        boolean insertStatus = false;
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            String password = PasswordStorage.createHash(pwd);
	            Statement stmt = dbConn.createStatement();
	            String query = "INSERT into user(username,email, password,shots, bottles,level,bio) values('"+name+ "',"+"'"
	                    + email + "','" + password +"',"+"'" + 0 + "','" + 0 + "','Beginner','Write something about yourself')";
	           System.out.println(query);
	            int records = stmt.executeUpdate(query);
	        
	            //When record is successfully inserted
	            if (records > 0) {
	                insertStatus = true;
	            }
	        } catch (SQLException sqle) {
	            sqle.printStackTrace();
	            throw sqle;
	        } catch (Exception e) {
	            //e.printStackTrace();
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return insertStatus;
	    }
	    public static List<FeedItem> getPosts() throws Exception {
	    	List<FeedItem> data = new ArrayList<>();
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = "SELECT * FROM feed order by id desc limit 10";
	            System.out.println(new Date()+" retrieving feeds from database ");
	            ResultSet rs = stmt.executeQuery(query);
	            while (rs.next()) {
	            	data.add(new FeedItem(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getInt(10),rs.getInt(9)));
	              
	               
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return data;
	    }
	    public static List<EventItem> getEventPost() throws Exception {
	    	List<EventItem> data = new ArrayList<>();
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = "SELECT * FROM event_feed order by id desc limit 10";
	            System.out.println(new Date()+" retriving event feeds from database");
	            ResultSet rs = stmt.executeQuery(query);
	            while (rs.next()) {
	            	data.add(new EventItem(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getInt(10),rs.getInt(9),rs.getBoolean(11)));
	                System.out.println(new Date()+" retrieving row"+data.size()+"from database ");
	               
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return data;
	    }
	    public static List<FeedItem> LoadMoreFeeds(String count) throws Exception {
	    	List<FeedItem> data = new ArrayList<>();
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = "SELECT * FROM feed where id <"+count +" order by id desc limit 10";
	            //System.out.println(query);
	            ResultSet rs = stmt.executeQuery(query);
	          
	            while (rs.next()) {
	            	data.add(new FeedItem(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getInt(10),rs.getInt(9)));
	                System.out.println(new Date()+" retrieving row"+data.size()+"from database ");
	               
	            }
	            
	           
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        	
	        return data;
	    }
	    public static List<EventItem> LoadMoreEventFeeds(String count) throws Exception {
	    	List<EventItem> data = new ArrayList<>();
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = "SELECT * FROM event_feed where id <"+count +" order by id desc limit 10";
	            //System.out.println(query);
	            ResultSet rs = stmt.executeQuery(query);
	          
	            while (rs.next()) {
	            	data.add(new EventItem(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getInt(10),rs.getInt(9),rs.getBoolean(11)));
	                System.out.println(new Date()+" retrieving row"+data.size()+"from database ");
	               
	            }
	            
	           
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        	
	        return data;
	    }
	    public static List<FeedItemDetails> getFeedDetails(String count) throws Exception {
	    	List<FeedItemDetails> data = new ArrayList<>();
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = "SELECT * FROM feed_item_details where ufdn ="+count;
	            //System.out.println(query);
	            ResultSet rs = stmt.executeQuery(query);
	          
	            while (rs.next()) {
	            	data.add(new FeedItemDetails(Integer.parseInt(rs.getString(1)),Integer.parseInt(rs.getString(2)), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
	                System.out.println(new Date() +" retrieving Item Details "+data.size()+"from database ");
	               
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        	
	        return data;
	    }
	    public static Boolean updateClientUserPassword(String username,String pwd) throws Exception {
	    	boolean success = false;
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = "";
	            String password = PasswordStorage.createHash(pwd);
	            
	            	 query = "UPDATE client SET password ='"+password+"' WHERE name='"+username+"';";
	            
	            //System.out.println(query);
	            int update = stmt.executeUpdate(query);
	            if(update > 0)
	            {
	            	success = true;
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return success;
	    }
	    public static Boolean updateUserPassword(String username,String pwd,String currentpwd) throws Exception {
	    	boolean success = false;
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = "";
	            String password = PasswordStorage.createHash(pwd);
	        
	            query = "Select password where username ="+username;
	            
	            ResultSet rs = stmt.executeQuery(query);
	            boolean status=false;
	            while (rs.next()) {
	            	 status = PasswordStorage.verifyPassword(currentpwd, rs.getString("password"));
	            }
	            //System.out.println(query);
	            if(status)
	            {
	           	 query = "UPDATE user SET password ='"+password+"' WHERE name='"+username+"';";
	            	 int update = stmt.executeUpdate(query);
	         	            if(update > 0)
	         	            {
	         	            	success = true;
	         	            }
	            }
	           
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return success;
	    }
	    public static Boolean updateShots(String id,String status) throws Exception {
	    	boolean success = false;
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = "";
	            
	            if(status.toString().equals("true"))
	            	 query = "UPDATE feed SET shots= shots+1 where id="+id;
	            else
	            	query = "UPDATE feed SET shots= shots-1 where id="+id;
	            //System.out.println(query);
	            int update = stmt.executeUpdate(query);
	            if(update > 0)
	            {
	            	success = true;
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return success;
	    }
	    
	    public static Boolean insertNewFeed(String imageLocation,String thumbnail,String name,String location,String profilepic,int shots,String status,boolean isVideo) throws Exception {
	    	boolean success = false;
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = "";
	            
	           /* if(type.toString().equals("New"))
	            {
	            	 query =String.format("Insert into feed_item_details (feed_id,name,details,date_time,location,damage,shots,profilepic) values(%d,%s,%s,,%s,%d,%d,%s)",feed_id,name,status,location,damage,shots,profilepic);
		            int result = stmt.executeUpdate(query);
		            if(resulet > 0)
		            {
		            	query = String.format("INSERT INTO feed (name,location,image,status,profilepic,ufdn,shots,isVideo) values(%s,%s,%s,%s,%s,LAST_INSERT_ID(),%d,%b)",name,location,imageLocation,status,profilepic,shots,isVideo);
		            }
	            }*/
	             
	            	query = String.format("INSERT INTO event_feed (name,location,image,status,profilePic,url,shots,isVideo) values('%s','%s','%s','%s','%s','%s',%d,%b)",name,location,imageLocation,status,profilepic,thumbnail,shots,isVideo);
	          /*  else if(type.equalsIgnoreCase("Fresh"))
	            {
	            	query = String.format("INSERT INTO feed (name,location,image,status,profilepic,ufdn,shots,isVideo) values(%s,%s,%s,%s,%s,%d,%d,%b)",name,location,imageLocation,status,profilepic,ufdn,shots,isVideo);
	            }*/
	            System.out.println(query);
	            int update = stmt.executeUpdate(query);
	            if(update > 0)
	            {
	            	success = true;
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return success;
	    }
	    
	    public static List<UserProfile> getProfile(String uname) throws Exception {
	    	List<UserProfile> data = new ArrayList<>();
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = "SELECT id,username,name,bio,email,cell,shots,bottles,level,imageurl FROM user where username ='"+uname+"'";
	            //System.out.println(query);
	            ResultSet rs = stmt.executeQuery(query);
	          
	            while (rs.next()) {
	            	data.add(  new UserProfile(rs.getInt("id"),rs.getString("username"), rs.getString("name"),rs.getString("bio"),rs.getString("email"),rs.getString("cell"),rs.getString("shots"),rs.getString("bottles"),rs.getString("level"),rs.getString("imageurl")));
	                System.out.println(new Date() +" retrieving Profile Details for User:"+uname+" from database ");
	               
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        	
	        return data;
	    }
	    
	    public static Boolean updateUser(int id,String name,String username,String bio,String email,String cell,String image) throws Exception {
	    	boolean success = false;
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	          
	            String query = "";
	            Statement stmt = dbConn.createStatement();
	            if(username.isEmpty() && email.isEmpty())
	            {
	            	 query = String.format("UPDATE user SET name ='%s',bio='%s',cell='%s',imageurl='%s' where id=%d",name,bio,cell,image,id);
	            }
	            else
	            {
	            	query = String.format("UPDATE user SET name ='%s',username='%s',bio='%s',email='%s',cell='%s',imageurl='%s' where id=%d",name,username,bio,email,cell,image,id);
	            }
	            System.out.println(query);
	            int update = stmt.executeUpdate(query);
	            if(update > 0)
	            {
	            	success = true;
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return success;
	    }
	    
	    public static Boolean updateUserShots(String username,String password,int amount,int ufdn,int userid) throws Exception {
	    	boolean success = false;
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	          
	            String query = "";
	            Statement stmt = dbConn.createStatement();
	            
	            String varifypassword = PasswordStorage.createHash(password);
	            if(varifypassword.equals(password))
	            {
	            	 query = String.format("UPDATE User SET shots = shots-%d where username=%s",amount,username);
	            	int update = stmt.executeUpdate(query);
	 	            if(update > 0)
	 	            {
	 	            	query = "INSERT INTO attended (userid,feedid) VALUES("+userid+","+ufdn+")";
	 	            	
	 	            	update = stmt.executeUpdate(query);
	 	            	if(update > 0)
	 	            	{
	 	            		success = true;
	 	            	}
	 	            }
	            }
	            else
	            {
	            	success = false;
	            }
	           
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return success;
	    }
	    public static List<AttendedItem> getAttended(int id) throws Exception {
	    	List<AttendedItem> data = new ArrayList<>();
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = "select feed_item_details.name,feed_item_details.date_time,feed_item_details.profilepic from feed_item_details inner join attended on feed_item_details.ufdn = attended.feedid inner join user on attended.userid = user.id where user.id ="+id;
	            //System.out.println(query);
	            ResultSet rs = stmt.executeQuery(query);
	          
	            while (rs.next()) {
	            	data.add(new AttendedItem(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(1)));
	                System.out.println(new Date() +" retrieving Attended Details "+data.size()+" from database");
	               
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        	
	        return data;
	    }
	    
	    
	    public static List<SearchItem> getClubs() throws Exception {
	    	List<SearchItem> data = new ArrayList<>();
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = "SELECT * FROM clubs";
	            //System.out.println(query);
	            ResultSet rs = stmt.executeQuery(query);
	          
	            while (rs.next()) {
	            	data.add(new SearchItem(rs.getString(2),rs.getString(3),rs.getString(4)));
	                System.out.println(new Date() +" retrieving Club list "+data.size()+"from database ");
	               
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        	
	        return data;
	    }
	    
	    public static List<SearchItem> getEvents() throws Exception {
	    	List<SearchItem> data = new ArrayList<>();
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = "SELECT * FROM event";
	            ResultSet rs = stmt.executeQuery(query);
	          
	            while (rs.next()) {
	            	data.add(new SearchItem(rs.getString(2),rs.getString(3),rs.getString(4)));
	                System.out.println(new Date() +" retrieving event list "+data.size()+"from database ");
	               
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        	
	        return data;
	    } 

	    public static List<FeedItem> getSearchItem(String name,String dbname) throws Exception {
	    	List<FeedItem> data = new ArrayList<>();
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = String.format("SELECT * FROM %s where name='%s'",dbname,name);
	            System.out.println(query);
	            ResultSet rs = stmt.executeQuery(query);
	            while (rs.next()) {
	            	data.add(new FeedItem(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getInt(10),rs.getInt(9)));
	                System.out.println(new Date()+" retrieving Search row"+data.size()+"from database ");	         
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return data;
	    }
	    public static List<String> getCity() throws Exception {
	    	List<String> data = new ArrayList<>();
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = String.format("SELECT DISTINCT city FROM client");
	            System.out.println(query);
	            ResultSet rs = stmt.executeQuery(query);
	            System.out.println(new Date()+" Retrieving city data from database ");
	            while (rs.next()) {
	            	data.add(rs.getString("city"));         
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return data;
	    }
	    public static Map<String,String> getClientProfile(String username) throws Exception {
	    	Map<String,String> data = new HashMap<>();
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = String.format("SELECT ucn,name,address,suburb,profilePic FROM client where name='%s'",username);
	            ResultSet rs = stmt.executeQuery(query);
	            System.out.println(new Date()+" Retrieving client data from database ");
	            while (rs.next()) {
	            	data.put("ucn",String.valueOf(rs.getInt("ucn")));
	            	data.put("name",rs.getString("name"));
	            	data.put("address",rs.getString("address"));
	            	data.put("suburb",rs.getString("suburb"));
	            	data.put("profilepic",rs.getString("profilePic"));
	            	      
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return data;
	    }
	    public static List<Location> getMaplocation() throws Exception {
	    	List<Location> data = new ArrayList<>();
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DatabaseHandler.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = String.format("SELECT * FROM client");
	            ResultSet rs = stmt.executeQuery(query);
	            System.out.println(new Date()+" Retrieving map data from database ");
	            while (rs.next()) {
	            	data.add(new Location(rs.getString("name"),rs.getString("address"),rs.getString("suburb"),rs.getString("city"),rs.getString("longitude"),rs.getString("latitude"),rs.getString("profilepic")));         
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return data;
	    }
	    
}
