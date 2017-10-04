package com.turnup.jersey;

import java.sql.SQLException;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
//Path: http://localhost/<appln-folder-name>/register
@Path("/register")
public class Register {
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/register/doregister
    @Path("/doregister")  
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
    public String doLogin(@QueryParam("username") String name, @QueryParam("email") String email, @QueryParam("password") String pwd){
        String response = "";
        //System.out.println("Inside doLogin "+uname+"  "+pwd);
        int retCode = registerUser(name, email, pwd);
        if(retCode == 0){
            response = Utitlity.constructJSON("register",true);
        }else if(retCode == 1){
            response = Utitlity.constructJSON("register",false, "You are already registered");
        }else if(retCode == 2){
            response = Utitlity.constructJSON("register",false, "Special Characters are not allowed in Username and Password");
        }else if(retCode == 3){
            response = Utitlity.constructJSON("register",false, "Error occured");
        }
        return response;
 
    }
 
    private int registerUser(String name, String email, String pwd){
        System.out.println("Inside checkCredentials");
        int result = 3;
        if(Utitlity.isNotNull(email) && Utitlity.isNotNull(pwd)){
            try {
            		if(DatabaseHandler.insertUser(name, email, pwd))
            		{
                    System.out.println(new Date()+": USer "+name+" Registered");
                    result = 0;
                }
            } catch(SQLException sqle){
                System.out.println("RegisterUSer catch sqle");
                sqle.printStackTrace();
                //When Primary key violation occurs that means user is already registered
                if(sqle.getErrorCode() == 1062){
                    result = 1;
                } 
                //When special characters are used in name,username or password
                else if(sqle.getErrorCode() == 1064){
                    System.out.println(sqle.getErrorCode());
                    result = 2;
                }
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("Inside checkCredentials catch e ");
            	e.printStackTrace();
                result = 3;
            }
        }else{
            System.out.println("Inside checkCredentials else");
            result = 3;
        }
 
        return result;
    }
 
}
