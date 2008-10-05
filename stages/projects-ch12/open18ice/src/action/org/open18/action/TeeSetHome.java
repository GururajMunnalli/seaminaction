package org.open18.action;

import org.open18.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;

@Name("teeSetHome")
@Scope(ScopeType.CONVERSATION)
public class TeeSetHome extends EntityHome<TeeSet> {
	
	@Logger Log log;
	
	
	@In(create = true)
	CourseHome courseHome;

//	@Observer("settingTeeSet")
	public void setTeeSetId(Long id) {
		log.info("setting id to "+id);
		setId(id);
	}

	public Long getTeeSetId() {
		return (Long) getId();
	}

	@Override
	protected TeeSet createInstance() {
		log.info("creating new instance of TeeSetHome");
		TeeSet teeSet = new TeeSet();
		return teeSet;
	}

	public void wire() {
		getInstance();
		Course course = courseHome.getDefinedInstance();
		if (course != null) {
			getInstance().setCourse(course);
		}
	}

	public boolean isWired() {
		if (getInstance().getCourse() == null)
			return false;
		return true;
	}

	public TeeSet getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Tee> getTees() {
		return getInstance() == null ? null : new ArrayList<Tee>(getInstance()
				.getTees());
	}

}
