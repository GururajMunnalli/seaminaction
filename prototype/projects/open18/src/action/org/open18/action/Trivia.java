package org.open18.action;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.remoting.WebRemote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Name("trivia")
@Scope(ScopeType.SESSION)
public class Trivia {

	private List<TriviaQuestion> questions = new ArrayList<TriviaQuestion>(Arrays.asList(
		new TriviaQuestion("What is Tiger Woods' given first name?", "Eldrick"),
		new TriviaQuestion("How many career PGA Tour victories does Jack Nicklaus have?", "73"),
		new TriviaQuestion("Which holes make up Amen Corner at Augusta?", "11, 12, 13")
	));

	@WebRemote
	public boolean answerQuestion(String question, String response) {
		TriviaQuestion questionInstance = findQuestion(question);
		// bogus question
		if (questionInstance == null) return false;

		boolean result = questionInstance.getAnswer().equals(response);
		if (result) {
			questions.remove(questionInstance);
		}

		return result;
	}

	@WebRemote(exclude = "answer")
	public TriviaQuestion drawQuestion() {
		if (questions.size() == 0) return null;
		return questions.get(new Random().nextInt(questions.size()));
	}

	public TriviaQuestion findQuestion(String question) {
		for (TriviaQuestion candidate : questions) {
			if (candidate.getQuestion().equals(question)) {
				return candidate;
			}
		}

		return null;
	}
}
