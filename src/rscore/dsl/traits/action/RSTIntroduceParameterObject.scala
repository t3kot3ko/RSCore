package rscore.dsl.traits.action
import rscore.dsl.entity.RSMethod
import rscore.action.refactoring.IntroduceParameterObjectRefactoringProcessor

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