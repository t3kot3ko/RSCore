package rscore.action.refactoring
import rscore.dsl.common.RSObject
import rscore.dsl.entity.RSMethod
import org.eclipse.jdt.internal.corext.refactoring.code.IntroduceIndirectionRefactoring
import rscore.helper.RefactoringHelper

class IntroduceIndirectionRefactoringProcessor(rsObject: RSObject) extends AbstractRefactoringProcessor{
	override def createAction(): RSRefactoringAction = {
		rsObject match{
			case m: RSMethod => return createActionForMethod(m)
		}
		
		return new RSRefactoringAction(Seq())
	}
	
	private def createActionForMethod(m: RSMethod): RSRefactoringAction = {
		val action: (() => Unit) = 
			() => {
				val refactoring = new IntroduceIndirectionRefactoring(m.origin)
				RefactoringHelper.performRefactoring(refactoring)
				
				// TODO: add(wrap) some options
			}
			
		return new RSRefactoringAction(Seq(action))
		
	}

}