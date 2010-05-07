package de.jax2010.grocerylist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@RequestScoped
public class GroceryListProducer
{
   @Inject EntityManager em;

   private List<GroceryItem> items;

   @Produces @Named
   public List<GroceryItem> getGroceryList() {
      return items;
   }

   @Produces @Remaining @Named
   public List<GroceryItem> getRemainingGroceryList() {
      List<GroceryItem> remaining = new ArrayList<GroceryItem>(items);
      for (Iterator<GroceryItem> it = items.iterator(); it.hasNext();) {
         if (it.next().isRetrieved()) {
            it.remove();
         }
      }
      return remaining;
   }

   public void update(@Observes(notifyObserver = Reception.IF_EXISTS) GroceryItem item) {
      fetch();
   }

   @PostConstruct
   public void fetch() {
      items = em.createQuery("select g from GroceryItem g order by g.name").getResultList();
   }
}
