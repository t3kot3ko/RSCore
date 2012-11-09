package dsl.traits.action
import dsl.entity.RSField
import core.action.refactoring.RenameFieldRefactoringProcessor
import dsl.entity.RSEntity

/**
 * 名前の変更リファクタリングを提供するトレイト
 */
trait RSTRenameRefactoring extends RefactoringTrait {
	// val self: RSEntity
	def rename(newName: String): Unit = {
		self match {
			case f: RSField => {
				val processor = new RenameFieldRefactoringProcessor(self.asInstanceOf[RSField], newName)
				processor.createAction().perform()
			}
			// case m: RSMethod => RenameFieldRefactoringProcessor.createAction(self, newName).perform()	
		}
		println("rename refactoring is completed")
	}

}