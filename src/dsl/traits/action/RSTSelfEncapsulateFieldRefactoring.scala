package dsl.traits.action
import dsl.entity.RSField
import dsl.entity.collection.RSFields
import core.action.refactoring.SelfEncapsulateFieldRefactoringProcessor

trait RSTSelfEncapsulateFieldRefactoring extends RefactoringTrait {
	def encapsulate(): Unit = {
		self match {
			case f: RSField => {
				println("encapsulate to RSField")
				val processor = new SelfEncapsulateFieldRefactoringProcessor(f)
				processor.createAction().perform()
			}
			case fs: RSFields => {
				println("encapsulate to RSFields")
				val processor = new SelfEncapsulateFieldRefactoringProcessor(fs)
				processor.createAction().perform()
			}
			case _ => throw new Exception("Receiver is not RSField or RSFields")

		}

	}
}