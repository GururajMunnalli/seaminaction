package de.jax2010.grocerylist;

import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Stateful
@Model
public class GroceryListManager {

   @Inject EntityManager em;

   @Inject Event<GroceryItem> itemEventSrc;

   private GroceryItem item;

   @Produces @Named
   public GroceryItem getNewItem() {
      if (item == null) {
         initNewItem();
      }
      return item;
   }

   public void markRetrieved(Long id)
   {
      GroceryItem targetItem = em.find(GroceryItem.class, id);
      targetItem.setRetrieved(true);
      itemEventSrc.fire(targetItem);
   }

   public void add(GroceryItem newItem) {
      System.out.println("Adding " + newItem);
      em.persist(newItem);
      itemEventSrc.fire(newItem);
      initNewItem();
   }

   public void delete(Long id) {
      GroceryItem targetItem = em.find(GroceryItem.class, id);
      em.remove(targetItem);
      itemEventSrc.fire(targetItem);
   }

   public String reset() {
      em.createQuery("delete from GroceryItem").executeUpdate();
      return "home?faces-redirect=true";
   }

   private void initNewItem() {
      GroceryItem i = new GroceryItem();
      i.setQuantity(1);
      i.setRetrieved(false);
      item = i;
   }
}
