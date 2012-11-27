package rscore.action.refactoring
import rscore.action.AbstractActionProcessor

abstract class AbstractRefactoringProcessor extends AbstractActionProcessor{
	def createAction(): RSRefactoringAction
	
	private def checkCondition(): Boolean = {
		return true
	}
}