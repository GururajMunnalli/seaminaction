package org.open18.action;

import javax.persistence.EntityManager;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.remoting.WebRemote;
import org.open18.model.Favorite;

@Name("favoritesAction")
@Transactional
public class FavoritesAction {

	@In	private EntityManager entityManager;

	@WebRemote
	public Favorite addFavorite(Favorite favorite) {
		try {
			entityManager.persist(favorite);
			return favorite;
		} catch (Exception e) {
			return null;
		}
	}

	@WebRemote
	public boolean isFavorite(Long entityId, String entityName) {
		try {
			entityManager.createQuery("select f from Favorite f " +
				"where f.entityId = :id and f.entityName = :name")
				.setParameter("id", entityId)
				.setParameter("name", entityName)
				.getSingleResult();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
//	@WebRemote
//	public void deleteFavorites(String entityName) {
//		entityManager.createQuery(
//			"delete f from Favorite f where f.entityName = :name")
//			.setParameter("name", entityName)
//			.executeUpdate();
//	}
//
//	@WebRemote
//	public List<Facility> getFavoriteFacilities(Golfer golfer) {
//		return entityManager.createQuery(
//			"select f from Facility f, Favorite fav " +
//			"where fav.entityName = 'Facility' and " +
//			"fav.entityId = f.id and " +
//			"fav.golfer.id = :golferId")
//			.setParameter("golferId", golfer.getId())
//			.getResultList();
//	}
//	
//	@WebRemote(exclude = {"facility"})
//	public List<Course> getFavoriteCourses(Golfer golfer) {
//		return entityManager.createQuery(
//			"select c from Course c, Favorite f " +
//			"where f.entityName = 'Course' and " +
//			"f.entityId = c.id and " +
//			"f.golfer.id = :golferId")
//			.setParameter("golferId", golfer.getId())
//			.getResultList();
//	}
}
