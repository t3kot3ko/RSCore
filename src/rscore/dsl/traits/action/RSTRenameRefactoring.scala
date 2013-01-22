package rscore.dsl.traits.action
import rscore.dsl.entity.RSField
import rscore.action.refactoring.RSRenameFieldRefactoringProcessor
import rscore.dsl.entity.RSEntity
import rscore.dsl.entity.RSMethod
import rscore.action.refactoring.RSRenameMethodRefactoringProcessor
import rscore.dsl.entity.RSParameter
import rscore.action.refactoring.RSRenameParameterRefactoringProcessor

/**
 * 名前の変更リファクタリングを提供するトレイト
 */
trait RSTRenameRefactoring extends RefactoringTrait {
	// val self: RSEntity
	def rename(newName: String): Unit = {
		val processor =
			self match {
				case f: RSField => new RSRenameFieldRefactoringProcessor(f, newName)
				case m: RSMethod => new RSRenameMethodRefactoringProcessor(m, newName)
				case p: RSParameter => new RSRenameParameterRefactoringProcessor(p, newName)
			}
		processor.createAction().perform()
		println("rename refactoring is completed")
	}

}