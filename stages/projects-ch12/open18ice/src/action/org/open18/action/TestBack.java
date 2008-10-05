package org.open18.action;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.ScopeType;

@Name("testBack")
@Scope(ScopeType.CONVERSATION)
public class TestBack {

	public List<Integer> scores = new ArrayList<Integer>(18);
	public List<Integer> putts = new ArrayList<Integer>(18);
	
	@Create
	public void init(){		
		System.out.println("creating testBack");
		for (int i=0; i<18;i++){
			scores.add(i);
			putts.add(2);
		}
	}
	@BypassInterceptors
	public List<Integer> getScores() {
		return scores;
	}

	public void setScores(List<Integer> scores) {
		this.scores = scores;
	}

	@BypassInterceptors
	public List<Integer> getPutts() {
		return putts;
	}

	public void setPutts(List<Integer> putts) {
		this.putts = putts;
	}
	@Destroy
	public void destroy(){
		System.out.println("Destroying testBack");
	}
}
