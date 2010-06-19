package com.socialize.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "update")
public class Update implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    private String text;

    public Update()
    {}

    public Update(final long id, final User user, final String text)
    {
        this.id = id;
        this.user = user;
        this.text = text;
        created = new Date();
    }

    @GET
    @Transient
    public Update get() {
        return this;
    }

    @Id
    @GeneratedValue
    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    @GET
    @Path("/user")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    public User getUser()
    {
        return user;
    }

    public void setUser(final User user)
    {
        this.user = user;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(final Date created)
    {
        this.created = created;
    }

    public String getText()
    {
        return text;
    }

    public void setText(final String text)
    {
        this.text = text;
    }
}
