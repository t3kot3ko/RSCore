package core.action.refactoring

abstract class AbstractRefactoringProcessor {
	def createAction(): RSRefactoringAction
	
	private def checkCondition(): Boolean = {
		return true
	}
}