package core.action.refactoring
import core.action.AbstractActionProcessor

abstract class AbstractRefactoringProcessor extends AbstractActionProcessor{
	def createAction(): RSRefactoringAction
	
	private def checkCondition(): Boolean = {
		return true
	}
}