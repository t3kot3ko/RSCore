package rscore.action.refactoring
import rscore.dsl.common.RSObject
import rscore.dsl.entity.RSMethod
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameMethodProcessor
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameNonVirtualMethodProcessor
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring
import rscore.helper.RefactoringHelper

class RenameMethodRefactoringProcessor(method: RSMethod, newName: String) extends AbstractRefactoringProcessor {
	override def createAction(): RSRefactoringAction = {
		val action: (() => Unit) =
			() => {
				val origin = method.origin()
				val processor = new RenameNonVirtualMethodProcessor(origin)
				processor.setNewElementName(newName)
				val refactoring = new RenameRefactoring(processor)
				val status = RefactoringHelper.performRefactoring(refactoring)
			}
		return new RSRefactoringAction(Seq(action))

	}

}