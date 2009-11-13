package com.socialize.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "updates")
public class Timeline implements Serializable
{
   private List<Update> updates = new ArrayList<Update>();

   public Timeline() {}
   
   public Timeline(List<Update> updates) {
      this.updates = updates;
   }
   
   @XmlElement(name = "update")
   public List<Update> getUpdates()
   {
      return updates;
   }

   public void setUpdates(List<Update> updates)
   {
      this.updates = updates;
   }
}
