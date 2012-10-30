package dsl.traits.action
import core.action.refactoring.AbstractRefactoringProcessor
import core.action.refactoring.RSRefactoringAction
import dsl.entity.RSClass
import core.action.refactoring.IntroduceFactoryRefactoringProcessor

trait RSTIntroduceFactory extends RefactoringTrait{
	def introduce_factory(): Unit = {
		self match{
			case c: RSClass => {
				val processor = new IntroduceFactoryRefactoringProcessor(c)
				processor.createAction().perform()
			}
		}
	}

}