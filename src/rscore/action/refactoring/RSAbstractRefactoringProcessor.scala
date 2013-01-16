package rscore.action.refactoring
import rscore.action.RSAbstractActionProcessor

abstract class RSAbstractRefactoringProcessor extends RSAbstractActionProcessor{
	def createAction(): RSRefactoringAction
	
	private def checkCondition(): Boolean = {
		return true
	}
}