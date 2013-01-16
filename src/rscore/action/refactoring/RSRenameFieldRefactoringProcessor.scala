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
import rscore.action.refactoring.RSAbstractRefactoringProcessor
import rscore.helper.RSRefactoringResult
import rscore.helper.RefactoringExecutionException

class RSRenameFieldRefactoringProcessor(rsField: RSField, newFieldName: String) extends RSAbstractRefactoringProcessor {
	override def createAction(): RSRefactoringAction = {
		val action: (() => Unit) =
			() => {
				try {
					val field = rsField.origin()
					val processor: RenameFieldProcessor = new RenameFieldProcessor(field)
					processor.setNewElementName(newFieldName)

					val refactoring: RenameRefactoring = new RenameRefactoring(processor)
					var result: RSRefactoringResult = RefactoringHelper.performRefactoring(refactoring)
					
					// TODO: Undo procedures
					
					
				}
				catch{
					case e: RefactoringExecutionException => println(e.getStatus())
				}
				
			}
		return new RSRefactoringAction(Seq(action))
	}

}