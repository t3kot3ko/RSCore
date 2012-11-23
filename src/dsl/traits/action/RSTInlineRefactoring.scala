package dsl.traits.action
import dsl.entity.RSField
import core.action.refactoring.InlineRefactoringProcessor

trait RSTInlineRefactoring extends RefactoringTrait{
	def inline(): Unit = {
		self match{
			case f: RSField =>
				val processor = new InlineRefactoringProcessor(f)
				processor.createAction().perform()
		}
		println("inline refactoring is done")
	}
}