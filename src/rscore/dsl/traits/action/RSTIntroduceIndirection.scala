package rscore.dsl.traits.action
import rscore.action.refactoring.IntroduceIndirectionRefactoringProcessor
import rscore.dsl.entity.RSMethod

trait RSTIntroduceIndirection extends RefactoringTrait{
	def introduceIndirection(): Unit = {
		self match {
			case m: RSMethod => {
				val processor = new IntroduceIndirectionRefactoringProcessor(m)
				processor.createAction().perform()
			}
		}
	}

}