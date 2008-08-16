package org.open18.action

@Name("multiRoundAction")
class MultiRoundAction {
	@In private def entityManager
	@In private def roundList
	void delete() {
		roundList.resultList.findAll { r -> r.selected }
			.each { r -> entityManager.remove r }
		roundList.refresh()
		"/RoundList.xhtml"
	}
}
