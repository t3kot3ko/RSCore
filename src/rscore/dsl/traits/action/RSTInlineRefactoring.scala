package rscore.dsl.traits.action
import rscore.dsl.entity.RSField
import rscore.action.refactoring.InlineRefactoringProcessor

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