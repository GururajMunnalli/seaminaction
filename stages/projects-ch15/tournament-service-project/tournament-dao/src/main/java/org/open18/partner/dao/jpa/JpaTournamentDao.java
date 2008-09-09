package org.open18.partner.dao.jpa;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.open18.partner.dao.TournamentDao;
import org.open18.partner.model.Tournament;

public class JpaTournamentDao implements TournamentDao {
	
	private static Log log = LogFactory.getLog(TournamentDao.class);
	
	private EntityManager em;
	
	public List<Tournament> getAll() {
		return em.createQuery("select t from Tournament t order by t.startDate asc").getResultList();
	}
	
	public List<Tournament> getUpcoming() {
		return em.createQuery(
			"select t from Tournament t where t.startDate > :now order by t.startDate asc")
			.setParameter("now", new Date())
			.getResultList();
	}
	
	public List<Tournament> queryForList(String pattern, String sortBy, int pageSize, int page) {
		// NOTE: we are are being very trusting with the sortBy value; you will want to sanitize it!
		return em.createQuery(
			"select t from Tournament t where lower(t.name) like :pattern order by t." + sortBy)
			.setParameter("pattern", pattern)
			.setFirstResult(page * pageSize)
			.setMaxResults(pageSize)
			.getResultList();
	}
	
	public List<Tournament> queryForList(String pattern) {
		return em.createQuery(
			"select t from Tournament t where lower(t.name) like :pattern")
			.setParameter("pattern", pattern)
			.getResultList();
	}
	
    public Tournament get(Long id) {
        return em.find(Tournament.class, id);
    }
	
	public void save(Tournament tournament) {
        if (tournament.getId() == null) {
			log.info("Persisting new tournament");
            em.persist(tournament);
        }
        else if (!em.contains(tournament)) {
			log.info("Merging the tournament. This is dirty!!");
			em.merge(tournament);
        }
		else {
			log.info("Letting the persistence manager update the tournament");
		}
		em.flush();
    }
	
	public void remove(Long id) {
		Tournament tournament = get(id);
		if (tournament != null) {
			em.remove(tournament);
			em.flush();
		}
	}
	
	public boolean exists(Tournament tournament) {
		Tournament existing = null;
		try {
			existing = (Tournament) em.createQuery("select t from Tournament t " +
				"where t.name = :name and " +
				"t.hostFacilityName = :facilityName and " +
				"t.hostFacilityLocation = :facilityLocation and " +
				"t.startDate = :startDate")
				.setParameter("name", tournament.getName())
				.setParameter("facilityName", tournament.getHostFacilityName())
				.setParameter("facilityLocation", tournament.getHostFacilityLocation())
				.setParameter("startDate", tournament.getStartDate())
				.getSingleResult();
		} catch (NoResultException e) {}
		
		if (existing != null && (tournament.getId() == null || !tournament.getId().equals(existing.getId()))) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean contains(Tournament tournament) {
		return this.em.contains(tournament);
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.em = entityManager;
	}

}
