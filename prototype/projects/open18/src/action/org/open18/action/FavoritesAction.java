package org.open18.action;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.remoting.WebRemote;
import org.open18.model.Course;
import org.open18.model.Favorite;
import org.open18.model.Golfer;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

@Name("favoritesAction")
@Scope(ScopeType.CONVERSATION)
@Transactional
public class FavoritesAction implements Serializable {
	@In
	EntityManager entityManager;

	@WebRemote
	//@Begin
	public Favorite addFavorite(Favorite favorite) {
		entityManager.persist(favorite);
		return favorite;
	}

	@WebRemote
	public boolean isFavorite(Long entityId, String entityName) {
		try {
			entityManager.createQuery("select f from Favorite f where f.entityId = :entityId and f.entityName = :entityName")
				.setParameter("entityId", entityId)
				.setParameter("entityName", entityName)
				.getSingleResult();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@WebRemote
	//@End
	public void checkManaged(Favorite favorite) {
		System.out.println("Managed? " + (entityManager.contains(favorite) ? "yes" : "no"));
	}

	@WebRemote
	public void removeAllFavorites() {
		entityManager.createQuery("delete from Favorite").executeUpdate();	
	}

	@WebRemote(exclude = {"facility"})
	public List<Course> getFavoriteCourses(Golfer golfer) {
		return entityManager.createQuery(
			"select c from Course c, Favorite f " +
			"where f.entityName = 'Course' and " +
			"f.entityId = c.id and " +
			"f.golfer.id = :golferId")
			.setParameter("golferId", golfer.getId())
			.getResultList();
	}
}