package core.action.refactoring
import dsl.common.RSObject
import dsl.entity.RSMethod
import org.eclipse.jdt.internal.corext.refactoring.structure.IntroduceParameterObjectProcessor
import org.eclipse.jdt.core.refactoring.descriptors.IntroduceParameterObjectDescriptor
import core.helper.RefactoringHelper
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring

class IntroduceParameterObjectRefactoringProcessor(rsObject: RSObject) extends AbstractRefactoringProcessor {
	override def createAction(): RSRefactoringAction = {
		rsObject match {
			case m: RSMethod => return createActionForMethod(m: RSMethod)
		}
		return null
	}

	private def createActionForMethod(m: RSMethod): RSRefactoringAction = {
		val action: (() => Unit) =
			() =>
				{
					val descripter = new IntroduceParameterObjectDescriptor()
					descripter.setMethod(m.origin())

					val processor = new IntroduceParameterObjectProcessor(descripter)
					val refactoring = new ProcessorBasedRefactoring(processor)
					RefactoringHelper.performRefactoring(refactoring)
				}

		return new RSRefactoringAction(Seq(action))
	}

}