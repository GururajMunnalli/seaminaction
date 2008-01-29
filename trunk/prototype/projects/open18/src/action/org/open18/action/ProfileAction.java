package org.open18.action;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Log;
import org.open18.ProfileNotFoundException;
import org.open18.model.Golfer;
import org.open18.model.GolferFriend;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Name("profileAction")
@Scope(ScopeType.CONVERSATION) // let's us redirect without losing @Out
public class ProfileAction {
    @Logger private Log log;

    @In protected EntityManager entityManager;

    @RequestParameter protected Long golferId;

    @RequestParameter protected String username;

    //@DataModelSelection
    @Out(required = false)
    protected Golfer selectedGolfer;

    @Out protected boolean profileLoaded = false;

    @DataModel//(scope = ScopeType.PAGE)
    protected List<Golfer> newGolfers;

    private int newGolferListDisplaySize = 3;

    private int newGolferListPoolSize = 10;

    public int getNewGolferListDisplaySize() {
        return newGolferListDisplaySize;
    }

    public void setNewGolferListDisplaySize(int newGolferListDisplaySize) {
        this.newGolferListDisplaySize = newGolferListDisplaySize;
    }

    public int getNewGolferListPoolSize() {
        return newGolferListPoolSize;
    }

    public void setNewGolferListPoolSize(int newGolferListPoolSize) {
        this.newGolferListPoolSize = newGolferListPoolSize;
    }

    public String view(Golfer selectedGolfer) {
        this.selectedGolfer = selectedGolfer;
        return view();
    }

    public String view() {
        log.info("profileAction.view() action called");
        log.info(selectedGolfer);
        assert selectedGolfer != null && selectedGolfer.getId() != null;
        profileLoaded = true;
        return "/profile.xhtml";
    }

    public void loadProfile() throws ProfileNotFoundException {
        if (selectedGolfer != null && selectedGolfer.getId() != null) {
            profileLoaded = true;
            return;
        }

        if (golferId != null && golferId > 0) {
            selectedGolfer = entityManager.find(Golfer.class, golferId);
        }
		else if (username != null && !"".equals(username.trim())) {
			try {
				selectedGolfer = (Golfer) entityManager.createQuery("select g from Golfer g where g.username = :username")
					.setParameter("username", username)
					.getSingleResult();
			}
			catch (EntityNotFoundException e) {
			}
		}

        if (selectedGolfer == null) {
            throw new ProfileNotFoundException(golferId);
        }
        else {
            profileLoaded = true;
        }
    }

    // QUESTION: I am still considering if a factory is the right approach here
	@Factory(value = "selectedGolferFriends", scope = ScopeType.PAGE)
    public List<GolferFriend> getFriends() {
		System.out.println("called me");
        if (selectedGolfer == null) {
            return null;
        }

        List<GolferFriend> friends = new ArrayList<GolferFriend>();
        for (GolferFriend relationship : selectedGolfer.getFriends()) {
            friends.add(relationship);
        }

        // QUESTION: does friend need to be loaded eagerly?
		// QUESTION: are null checks necessary?
		// FIXME: this Comparator needs to be extracted!!!
        Collections.sort(friends, new Comparator<GolferFriend>() {
            public int compare(GolferFriend a, GolferFriend b) {
				Golfer golferA = a.getFriend();
				Golfer golferB = b.getFriend();
				if (golferA.getLastName().equals(golferB.getLastName())) {
					return golferA.getFirstName().compareTo(golferB.getFirstName());
				}
				else {
					return golferA.getLastName().compareTo(golferB.getLastName());
				}
            }
        });

        return friends;
    }

    //@Factory("newGolfers")
    public void findNewGolfers() {
        @SuppressWarnings("unchecked")
        List<Golfer> results = entityManager
          .createQuery("select g from Golfer g order by g.dateJoined desc")
          .setMaxResults(newGolferListPoolSize)
          .getResultList();

        Collections.shuffle(results);

        Random random = new Random();

        while (results.size() > newGolferListDisplaySize) {
            results.remove(random.nextInt(results.size()));
        }

        newGolfers = results;
    }

    //@Factory("newGolfers")
    public void findNewGolfersFromCache() {
        newGolfers = (List<Golfer>) Component.getInstance("newGolfersCache");    
    }
}
