package org.open18.action;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.remoting.WebRemote;
import org.open18.model.TriviaQuestion;

@Name("adhocTrivia")
@Scope(ScopeType.SESSION)
public class AdhocTrivia implements Serializable {

	@In	private EntityManager entityManager;
	private List<TriviaQuestion> questions;

	@Create
	public void init() {
		questions = entityManager.createQuery(
			"select q from TriviaQuestion q").getResultList();
	}

	@WebRemote
	public boolean answerQuestion(Long id, String response) {
		TriviaQuestion questionInstance = findQuestion(id);
		if (questionInstance == null) {
			return false;
		}
		return questionInstance.getAnswer().equals(response);
	}

	@WebRemote(exclude = "answer")
	public TriviaQuestion drawQuestion() {
		if (questions.size() == 0) {
			return null;
		}
		return questions.get(new Random().nextInt(questions.size()));
	}

	public TriviaQuestion findQuestion(Long id) {
		for (TriviaQuestion candidate : questions) {
			if (candidate.getId().equals(id)) {
				return candidate;
			}
		}
		return null;
		//return entityManager.find(TriviaQuestion.class, id);
	}
}