package rscore.dsl.traits.action
import rscore.dsl.entity.RSField
import rscore.action.refactoring.RSSelfEncapsulateFieldRefactoringProcessor
import rscore.dsl.entity.collection.RSCollection

trait RSTSelfEncapsulateFieldRefactoring extends RefactoringTrait {
	def encapsulate(): Unit = {
		self match {
			case f: RSField => {
				println("encapsulate to RSField")
				val processor = new RSSelfEncapsulateFieldRefactoringProcessor(f)
				processor.createAction().perform()
			}
			case fs: RSCollection[RSField] => {
				println("encapsulate to RSFields")
				val processor = new RSSelfEncapsulateFieldRefactoringProcessor(fs)
				processor.createAction().perform()
			}
			case _ => throw new Exception("Receiver is not RSField or RSFields")

		}

	}
}