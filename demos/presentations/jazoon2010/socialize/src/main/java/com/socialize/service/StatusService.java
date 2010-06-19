package com.socialize.service;


import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.socialize.model.Update;

@Name("statusService")
@Path("/")
public class StatusService
{
    @In
    EntityManager entityManager;

    @Path("/updates/{id:[0-9]+}")
    @Produces(MediaType.APPLICATION_XML)
    public Update getUpdate(@PathParam("id") long id)
    {
        return (Update) entityManager.createQuery(
                "select u from Update u where u.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }
}
