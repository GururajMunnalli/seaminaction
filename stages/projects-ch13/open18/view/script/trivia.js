function askQuestion() {
	loadMessages();
	var trivia = Seam.Component.getInstance("adhocTrivia");
	trivia.drawQuestion(poseQuestion);
}

function poseQuestion(triviaQuestion) {
	if (triviaQuestion == null) {
		alert("Sorry, there are no trivia questions.");
	}
	else if (triviaQuestion.getAnswer() != undefined) {
		alert("This quiz has been compromised!");
	}
	else {
		var response = window.prompt(triviaQuestion.question);
		if (response) {
			var trivia = Seam.Component.getInstance("adhocTrivia");
			trivia.answerQuestion(triviaQuestion.getId(), response, reportResult);
		}
	}
}

function reportResult(result) {
	alert(result ? messages.get("response.correct") : messages.get("response.incorrect"));
}
