package com.socialize.service;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.socialize.model.User;

@Name("userService")
@Path("/user")
public class UserService
{
   @In EntityManager entityManager;
   
   @GET
   @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/x-vCard"})
   public User getUser(@QueryParam("screen_name") String screenName) {
      User user = (User) entityManager
         .createQuery("select u from User u where u.screenName = :screen_name")
         .setParameter("screen_name", screenName)
         .getSingleResult();
      return user;
   }
   
   /*
   @GET
   @Produces("text/x-vCard")
   public String getUserAsVCard(@QueryParam("screen_name") String screenName) {
      User user = getUser(screenName);
      StringBuilder vcard = new StringBuilder("BEGIN:VCARD\nVERSION:3.0\n");
      vcard.append("FN:" + user.getName() + "\n");
      vcard.append("ADR;TYPE=HOME:;;" + user.getLocation().replace(",", "\\,") + ";;;;\n");
      vcard.append("URL:" + user.getUrl() + "\n");
      vcard.append("REV:" + new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'").format(new Date()) + "\n");
      vcard.append("END:VCARD");
      return vcard.toString();
   }*/
}
