package rscore.action.refactoring
import rscore.dsl.common.RSObject
import rscore.dsl.entity.RSMethod
import org.eclipse.jdt.internal.corext.refactoring.structure.IntroduceParameterObjectProcessor
import org.eclipse.jdt.core.refactoring.descriptors.IntroduceParameterObjectDescriptor
import rscore.helper.RefactoringHelper
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring

class RSIntroduceParameterObjectRefactoringProcessor(rsObject: RSObject, name: String) extends RSAbstractRefactoringProcessor {
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
					
					if (name != "") {
						descripter.setClassName(name)
					}

					val processor = new IntroduceParameterObjectProcessor(descripter)
					val refactoring = new ProcessorBasedRefactoring(processor)
					RefactoringHelper.performRefactoring(refactoring)
				}

		return new RSRefactoringAction(Seq(action))
	}

}