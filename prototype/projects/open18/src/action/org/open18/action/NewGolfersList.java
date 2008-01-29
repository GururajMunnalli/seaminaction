package org.open18.action;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.open18.model.Golfer;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Name("newGolfersList")
@Scope(ScopeType.APPLICATION)
public class NewGolfersList {

	private int poolSize = 25;

	private int displaySize = 5;

	@In EntityManager entityManager;

	protected List<Golfer> newGolfers;
	
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public void setDisplaySize(int displaySize) {
		this.displaySize = displaySize;
	}

	@Create
	public void onCreate() {
		fetchNewGolfers();
	}

	@Unwrap
	public List<Golfer> getNewGolfers() {
		return newGolfers;
	}

	@Observer(value = "golferRegistered", create = false)
	synchronized public void fetchNewGolfers() {
		@SuppressWarnings("unchecked")
		List<Golfer> results = entityManager
			.createQuery("select g from Golfer g order by g.dateJoined desc")
			.setMaxResults(poolSize)
			.getResultList();

		Collections.shuffle(results);

		Random random = new Random();
		while (results.size() > displaySize) {
			results.remove(random.nextInt(results.size()));
		}

		newGolfers = results;
	}

}
