package com.socialize.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import com.socialize.model.User;

@Name("timelineService")
@Path("/timeline")
public class TimelineService
{
    private final static String COUNT_DEFAULT = "5";

    private final static int MAX_COUNT_ALLOWED = 200;

    @In
    EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Timeline getUpdates(@QueryParam("count") @DefaultValue(COUNT_DEFAULT) final int count)
    {
        @SuppressWarnings("unchecked")
        List<Update> updates = entityManager.createQuery("select u from Update u order by u.created asc")
                .setMaxResults(Math.min(count, MAX_COUNT_ALLOWED)).getResultList();
        return new Timeline(updates);
    }

    @GET
    @Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{screenName}")
    public Timeline getUpdatesForUser(@PathParam("screenName") final String screenName,
            @QueryParam("count") @DefaultValue(COUNT_DEFAULT) final int count)
    {
        if (!("mojavelinux".equals(screenName) || "lincolnthree".equals(screenName)))
        {
            throw new NoSuchUserException(screenName);
        }

        @SuppressWarnings("unchecked")
        List<Update> updates = entityManager.createQuery(
                "select u from Update u where u.user.screenName = :screen_name order by u.created asc").setParameter(
                "screen_name", screenName).setMaxResults(Math.min(count, MAX_COUNT_ALLOWED)).getResultList();
        return new Timeline(updates);
    }

    @POST
    @Consumes( { MediaType.TEXT_PLAIN })
    @Produces( { MediaType.TEXT_PLAIN })
    @Path("/{screenName}")
    public long updateUserStatus(@PathParam("screenName") final String screenName,
            @QueryParam("status") final String status)
    {
        if (!("mojavelinux".equals(screenName) || "lincolnthree".equals(screenName)))
        {
            throw new NoSuchUserException(screenName);
        }

        User user = (User) entityManager.createNamedQuery("user.bySName").setParameter(":sn", screenName)
                .getSingleResult();

        Update u = new Update();
        u.setUser(user);
        u.setText(status);
        u.setCreated(new Date());
        entityManager.persist(u);

        return u.getId();
    }
}
