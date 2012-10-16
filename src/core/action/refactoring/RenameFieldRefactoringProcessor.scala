package core.action.refactoring
import dsl.action.RSAction
import dsl.entity.RSClass
import dsl.entity.collection.RSFields._
import dsl.entity.RSMethod
import dsl.common.RSParam
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameFieldProcessor

class RenameFieldRefactoringProcessor {
	def createAction(cls: RSClass, targetFieldName: String, newFieldName: String): RSAction = {
		var field = cls.fields.where(
			RSParam(
				"name" -> Array(targetFieldName))).first.origin

		var processor: RenameFieldProcessor = new RenameFieldProcessor(field)
		processor.setNewElementName(newFieldName)

		var refactoring: RenameRefactoring = new RenameRefactoring(processor)
		println("PERFORMING")
		var status = RefactoringHelper.performRefactoring(refactoring)
		if (status != null) {
			println(status)
		}
	}
}