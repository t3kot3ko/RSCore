package dsl.traits.action
import dsl.entity.RSField
import dsl.entity.collection.RSFields
import core.action.refactoring.SelfEncapsulateFieldRefactoringProcessor

trait RSTSelfEncapsulateFieldRefactoring extends RefactoringTrait {
	def encapsulate(): Unit = {
		self match {
			case f: RSField => {
				SelfEncapsulateFieldRefactoringProcessor.createAction(f.asInstanceOf[RSField]).perform()
			}
			case fs: RSFields => {
				SelfEncapsulateFieldRefactoringProcessor.createAction(fs.asInstanceOf[RSFields]).perform()
			}

		}

	}
}