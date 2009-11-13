package com.socialize.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.socialize.NoSuchUserException;
import com.socialize.model.Timeline;
import com.socialize.model.Update;

@Name("timelineService")
@Path("/timeline")
public class TimelineService
{
   private final static String COUNT_DEFAULT = "5";
   
   private final static int MAX_COUNT_ALLOWED = 200;
   
   @In EntityManager entityManager;
   
   @GET
   @Produces(MediaType.APPLICATION_XML)
   public Timeline getUpdates(@QueryParam("count") @DefaultValue(COUNT_DEFAULT) int count) {
      @SuppressWarnings("unchecked")
      List<Update> updates = entityManager
         .createQuery("select u from Update u order by u.created asc")
         .setMaxResults(Math.min(count, MAX_COUNT_ALLOWED))
         .getResultList();
      return new Timeline(updates);
   }
   
   @GET
   @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
   @Path("/{screenName}")
   public Timeline getUpdatesForUser(@PathParam("screenName") String screenName, @QueryParam("count") @DefaultValue(COUNT_DEFAULT) int count) {
      if (!("mojavelinux".equals(screenName) || "lincolnthree".equals(screenName))) {
         throw new NoSuchUserException(screenName);
      }
      
      @SuppressWarnings("unchecked")
      List<Update> updates = entityManager
         .createQuery("select u from Update u where u.user.screenName = :screen_name order by u.created asc")
         .setParameter("screen_name", screenName)
         .setMaxResults(Math.min(count, MAX_COUNT_ALLOWED))
         .getResultList();
      return new Timeline(updates);
   }
}
