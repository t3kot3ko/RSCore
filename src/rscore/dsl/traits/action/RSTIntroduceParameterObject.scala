package rscore.dsl.traits.action
import rscore.dsl.entity.RSMethod
import rscore.action.refactoring.RSIntroduceParameterObjectRefactoringProcessor

trait RSTIntroduceParameterObject extends RefactoringTrait {
	def introduceParameterObject(): Unit = {
		self match{
			case m: RSMethod => {
				val processor = new RSIntroduceParameterObjectRefactoringProcessor(m)
				processor.createAction().perform()
			}
		}
		println("introduce parameter object refactoring is successed")
	}
	
	//  just an alias
	def introduce_parameter_object(): Unit = {
		this.introduceParameterObject()
	}

}