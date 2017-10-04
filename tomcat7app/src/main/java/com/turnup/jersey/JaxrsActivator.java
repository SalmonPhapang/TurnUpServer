package com.turnup.jersey;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;

import com.turnup.posts.FeedItem;
import com.turnup.posts.Result;

@ApplicationPath("/TurnUpServer")
public class JaxrsActivator extends Application  {

     public Set<Class<?>> getClasses() {
         Set<Class<?>> s = new HashSet<Class<?>>();
         s.add(PostDataResource.class);
         s.add(Login.class);
         s.add(Register.class);
         s.add(DatabaseHandler.class);
         s.add(FeedItem.class);
         s.add(Utitlity.class);
         s.add(Constants.class);
         s.add(PasswordStorage.class);
         s.add(Result.class);
         return s;
     }
}

