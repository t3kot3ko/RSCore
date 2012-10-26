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

class RenameFieldRefactoringProcessor {
	def createAction(cls: RSClass, targetFieldName: String, newFieldName: String): Unit = {
		var field = cls.fields.select(By name With(targetFieldName)).first.origin
		var processor: RenameFieldProcessor = new RenameFieldProcessor(field)
		processor.setNewElementName(newFieldName)

		var refactoring: RenameRefactoring = new RenameRefactoring(processor)
		println("PERFORMING")
		var status = RefactoringHelper.performRefactoring(refactoring)
		if (status != null) {
			println(status)
		}
		
		return null
	}
}