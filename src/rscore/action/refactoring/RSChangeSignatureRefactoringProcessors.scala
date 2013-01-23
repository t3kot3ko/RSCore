package rscore.action.refactoring
import rscore.dsl.entity.RSMethod
import org.eclipse.jdt.internal.corext.refactoring.structure.ChangeSignatureProcessor
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import rscore.helper.RefactoringHelper

class RSChangeSignatureRefactoringProcessors {
	class ChangeReturnTypeProcessor(m: RSMethod, newReturnType: String) extends RSAbstractRefactoringProcessor {
		def createAction(): RSRefactoringAction = {
			val action: (() => Unit) =
				() => {
					val processor = new ChangeSignatureProcessor(m.origin)
					processor.setNewReturnTypeName(newReturnType)

					val refactoring = new ProcessorBasedRefactoring(processor)
					RefactoringHelper.performRefactoring(refactoring)
				}
			return new RSRefactoringAction(Seq(action))
		}
	}

}