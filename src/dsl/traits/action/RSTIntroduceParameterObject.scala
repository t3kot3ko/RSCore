package dsl.traits.action
import dsl.entity.RSMethod
import core.action.refactoring.IntroduceParameterObjectRefactoringProcessor

trait RSTIntroduceParameterObject extends RefactoringTrait {
	def introduceParameterObject(): Unit = {
		self match{
			case m: RSMethod => {
				val processor = new IntroduceParameterObjectRefactoringProcessor(m)
				processor.createAction().perform()
			}
		}
		println("introduce parameter object refactoring is successed")
	}

}