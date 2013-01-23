package rscore.dsl.traits.action
import rscore.dsl.entity.RSMethod
import rscore.action.refactoring.RSChangeSignatureRefactoringProcessors

trait RSTChangeSignatureRefactoring extends RefactoringTrait{
	def change_return_type(newName: String): Unit = this.changeReturnType(newName)
	def changeReturnType(newName: String): Unit = {
		self match{
			case m: RSMethod =>{ 
				val ps = new RSChangeSignatureRefactoringProcessors
				val processor = new ps.ChangeReturnTypeProcessor(m, newName)
				processor.createAction().perform()
			}
		}
	}

}