package com.socialize.client;

import com.socialize.model.Timeline;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

public interface TimelineClient {
   @GET
   @Path("/timeline")
   @Produces(MediaType.APPLICATION_XML)
   Timeline getUpdates(@QueryParam("count") int cnt);

   @GET
   @Path("/timeline/{screenName}")
   @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
   Timeline getUpdatesByUser(@PathParam("screenName") String screenName);
}
