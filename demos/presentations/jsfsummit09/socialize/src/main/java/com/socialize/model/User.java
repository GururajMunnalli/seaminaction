package com.socialize.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = "screen_name"))
@XmlRootElement(name = "user")
public class User
{
   private Long id;
   
   private String screenName;
   
   private String name;
   
   private String location;
   
   private String url;

   @Id @GeneratedValue
   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   @XmlElement(name = "screen_name")
   public String getScreenName()
   {
      return screenName;
   }

   public void setScreenName(String screenName)
   {
      this.screenName = screenName;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getLocation()
   {
      return location;
   }

   public void setLocation(String location)
   {
      this.location = location;
   }

   public String getUrl()
   {
      return url;
   }

   public void setUrl(String url)
   {
      this.url = url;
   }
}
