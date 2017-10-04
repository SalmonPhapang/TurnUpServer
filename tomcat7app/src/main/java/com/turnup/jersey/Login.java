package com.turnup.jersey;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
//Path: http://localhost/<appln-folder-name>/login
@Path("/login")
public class Login {
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/login/dologin
    @Path("/dologin")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://localhost/<appln-folder-name>/login/dologin?username=abc&password=xyz
    public  Map<String,String> doLogin(@QueryParam("username") String uname, @QueryParam("password") String pwd){
     
        return checkCredentials(uname, pwd);    
    }
 
    /**
     * Method to check whether the entered credential is valid
     * 
     * @param uname
     * @param pwd
     * @return
     */
    private  Map<String,String> checkCredentials(String uname, String pwd){
        System.out.println("Inside checkCredentials");
        Map<String,String> result = new HashMap();
        if(Utitlity.isNotNull(uname) && Utitlity.isNotNull(pwd)){
            try {
                result = DatabaseHandler.checkLogin(uname, pwd);
                //System.out.println("Inside checkCredentials try "+result);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                //System.out.println("Inside checkCredentials catch");
            	e.printStackTrace();
                result.put("status", "false");
            }
        }else{
            //System.out.println("Inside checkCredentials else");
        	result.put("status", "false");
        }
 
        return result;
    }
 
}
