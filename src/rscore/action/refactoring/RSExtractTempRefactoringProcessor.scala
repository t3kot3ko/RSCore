package rscore.action.refactoring
import rscore.dsl.detail.expression.RSExpression
import org.eclipse.jdt.internal.corext.refactoring.code.ExtractTempRefactoring

class RSExtractTempRefactoringProcessor(expression: RSExpression) extends RSAbstractRefactoringProcessor{
	override def createAction(): RSRefactoringAction = {
		// val cu = expression.origin.
		// val ref = new ExtractTempRefactoring()
		
		return null
	}

}