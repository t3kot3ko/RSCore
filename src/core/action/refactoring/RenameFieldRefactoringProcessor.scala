package core.action.refactoring
import dsl.entity.RSClass
import dsl.entity.RSMethod
import dsl.common.RSParam
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameFieldProcessor
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring
import core.helper.RefactoringHelper
import dsl.util.ImplicitConversions._
import dsl.entity.collection.By
import dsl.entity.collection.With
import dsl.entity.RSField
import org.eclipse.ltk.core.refactoring.RefactoringStatus
import core.action.refactoring.AbstractRefactoringProcessor

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