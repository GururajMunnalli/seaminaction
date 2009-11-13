package com.socialize.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "update")
public class Update implements Serializable
{
   private Long id;
   
   private User user;
   
   private Date created;
   
   private String text;

   public Update() {}
   
   public Update(long id, User user, String text) {
      this.id = id;
      this.user = user;
      this.text = text;
      this.created = new Date();
   }
   
   @Id @GeneratedValue
   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "USER_ID", nullable = false)
   public User getUser() {
      return this.user;
   }
   
   public void setUser(User user) {
      this.user = user;
   }

   public Date getCreated()
   {
      return created;
   }

   public void setCreated(Date created)
   {
      this.created = created;
   }

   public String getText()
   {
      return text;
   }

   public void setText(String text)
   {
      this.text = text;
   }
}
