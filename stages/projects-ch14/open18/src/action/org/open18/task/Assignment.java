package org.open18.task;

import org.jboss.seam.annotations.Name;

@Name("assignment")
public class Assignment {
	protected String actorId;

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
}
