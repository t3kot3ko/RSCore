package rscore.dsl.traits.action
import rscore.action.refactoring.RSIntroduceIndirectionRefactoringProcessor
import rscore.dsl.entity.RSMethod

trait RSTIntroduceIndirection extends RefactoringTrait{
	def introduceIndirection(): Unit = {
		self match {
			case m: RSMethod => {
				val processor = new RSIntroduceIndirectionRefactoringProcessor(m)
				processor.createAction().perform()
			}
		}
	}

}