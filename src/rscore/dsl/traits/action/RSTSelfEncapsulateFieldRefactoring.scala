package rscore.dsl.traits.action
import rscore.dsl.entity.RSField
import rscore.action.refactoring.SelfEncapsulateFieldRefactoringProcessor
import rscore.dsl.entity.collection.RSCollection

trait RSTSelfEncapsulateFieldRefactoring extends RefactoringTrait {
	def encapsulate(): Unit = {
		self match {
			case f: RSField => {
				println("encapsulate to RSField")
				val processor = new SelfEncapsulateFieldRefactoringProcessor(f)
				processor.createAction().perform()
			}
			case fs: RSCollection[RSField] => {
				println("encapsulate to RSFields")
				val processor = new SelfEncapsulateFieldRefactoringProcessor(fs)
				processor.createAction().perform()
			}
			case _ => throw new Exception("Receiver is not RSField or RSFields")

		}

	}
}