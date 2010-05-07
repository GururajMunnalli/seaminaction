package de.jax2010.grocerylist;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class GroceryItem implements Serializable
{
   private Long id;
   private String name;
   private String section;
   private int quantity;
   private boolean retrieved;
   
   public GroceryItem() {}

   @Id
   @GeneratedValue
   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   @NotNull
   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getSection()
   {
      return section;
   }

   public void setSection(String section)
   {
      this.section = section;
   }

   @Min(1)
   public int getQuantity()
   {
      return quantity;
   }

   public void setQuantity(int quantity)
   {
      this.quantity = quantity;
   }

   public boolean isRetrieved()
   {
      return retrieved;
   }

   public void setRetrieved(boolean retrieved)
   {
      this.retrieved = retrieved;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(GroceryItem.class.getSimpleName());
      sb.append("[");
      if (id != null) {
         sb.append("id=");
         sb.append(id);
      }
      else {
         sb.append("transient");
      }
      sb.append("; name=");
      sb.append(name);
      sb.append("; quantity=");
      sb.append(quantity);
      sb.append("; section=");
      sb.append(section);
      sb.append("; retrieved=");
      sb.append(retrieved);
      sb.append("]");
      return sb.toString();
   }
   
   /** Default value included to remove warning.  Remove or modify at will.  */  
   private static final long serialVersionUID = 1L;
}
