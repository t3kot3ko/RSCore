package rscore.dsl.traits.action
import rscore.dsl.entity.RSField
import rscore.action.refactoring.RSRenameFieldRefactoringProcessor
import rscore.dsl.entity.RSEntity
import rscore.dsl.entity.RSMethod
import rscore.action.refactoring.RSRenameMethodRefactoringProcessor

/**
 * 名前の変更リファクタリングを提供するトレイト
 */
trait RSTRenameRefactoring extends RefactoringTrait {
	// val self: RSEntity
	def rename(newName: String): Unit = {
		self match {
			case f: RSField => {
				val processor = new RSRenameFieldRefactoringProcessor(self.asInstanceOf[RSField], newName)
				processor.createAction().perform()
			}
			case m: RSMethod => {
				val processor = new RSRenameMethodRefactoringProcessor(m, newName)
				processor.createAction().perform()
			}
		}
		println("rename refactoring is completed")
	}

}