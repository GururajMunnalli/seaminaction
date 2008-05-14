package org.open18.golftips.action;

import java.util.List;
import javax.persistence.EntityManager;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.log.Log;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.contexts.Contexts;

import org.open18.model.GolfTip;

@Name("tipAction")
public class TipAction {

    @Logger private Log log;
    
	@In	EntityManager entityManager;

    @In FacesMessages facesMessages;
    
	// don't need to preserve DataModel when using ICEFaces
	@DataModel(scope = ScopeType.PAGE)
	private List<GolfTip> tips;

	// required if GolfTip entity class is not a defined as a Seam component
	//@Out(required = false)
	//private GolfTip tip;

	@DataModelSelection
	@Out(required = false)
	private GolfTip activeTip;

	@Factory("tips")
	public void retrieveAllTips() {
		log.info("Factory for tips called. Retrieving all golf tips...");
		tips = entityManager.createQuery("from GolfTip").getResultList();
	}

	public void add(GolfTip tip)
	{
		log.info("tipAction.add(GolfTip) action called");
		if (tip.getContent().trim().length() == 0) {
			FacesMessages.instance().add("Please provide a tip from which we may all learn.");
			return;
		}

		log.info("Adding new golf tip...");
		entityManager.persist(tip);
		activeTip = tip;
		facesMessages.add("Thanks for the tip, #{activeTip.author}!");
		retrieveAllTips();

		// required if GolfTip entity class is a Seam compnent (to clear reference)
		Contexts.removeFromAllContexts("tip");

		// required if GolfTip entity class is not a Seam compnent
		//resetTip();
	}

	public void delete() {
		log.info("tipAction.delete() action called");
		log.info("Removing golf tip...");
		entityManager.remove(entityManager.find(GolfTip.class, activeTip.getId()));
		facesMessages.add("The selected golf tip, contributed by #{activeTip.author}, has been deleted.");
		retrieveAllTips();
	}
	
	// required if GolfTip entity class is not a Seam component
	//@Factory("tip")
	//public void resetTip() {
	//	tip = new GolfTip();
	//}
}
