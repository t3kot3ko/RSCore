package rscore.action.refactoring
import rscore.dsl.entity.RSClass
import rscore.dsl.entity.RSMethod
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameFieldProcessor
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring
import rscore.helper.RefactoringHelper
import rscore.dsl.util.ImplicitConversions._
import rscore.dsl.entity.collection.By
import rscore.dsl.entity.collection.With
import rscore.dsl.entity.RSField
import org.eclipse.ltk.core.refactoring.RefactoringStatus
import rscore.action.refactoring.AbstractRefactoringProcessor

class RenameFieldRefactoringProcessor(rsField: RSField, newFieldName: String) extends AbstractRefactoringProcessor {
	override def createAction(): RSRefactoringAction = {
		val action: (() => Unit) =
			() => {
				val field = rsField.origin()
				val processor: RenameFieldProcessor = new RenameFieldProcessor(field)
				processor.setNewElementName(newFieldName)

				val refactoring: RenameRefactoring = new RenameRefactoring(processor)

				var status: RefactoringStatus = RefactoringHelper.performRefactoring(refactoring)
				if (status != null) {
					println(status)
				}
			}
		return new RSRefactoringAction(Seq(action))
	}

}