package rscore.dsl.traits.action
import rscore.action.refactoring.RSAbstractRefactoringProcessor
import rscore.action.refactoring.RSRefactoringAction
import rscore.dsl.entity.RSClass
import rscore.action.refactoring.RSIntroduceFactoryRefactoringProcessor
import rscore.dsl.entity.RSMethod

trait RSTIntroduceFactory extends RefactoringTrait{
	def introduce_factory(): Unit = {
		self match{
			case c: RSClass => {
				println("RSClass")
				val processor = new RSIntroduceFactoryRefactoringProcessor(c)
				processor.createAction().perform()
			}
			// コンストラクタが直接来た時
			case m: RSMethod => {
				println("RSMethod")
				val processor = new RSIntroduceFactoryRefactoringProcessor(m)
				processor.createAction().perform()
			}
			case _ => println("other")
			
		}
		println("introduce factory was completed")
	}

}