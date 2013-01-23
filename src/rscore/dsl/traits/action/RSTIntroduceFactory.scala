package rscore.dsl.traits.action
import rscore.action.refactoring.RSAbstractRefactoringProcessor
import rscore.action.refactoring.RSRefactoringAction
import rscore.dsl.entity.RSClass
import rscore.action.refactoring.RSIntroduceFactoryRefactoringProcessor
import rscore.dsl.entity.RSMethod

trait RSTIntroduceFactory extends RefactoringTrait{
	def introduce_factory(): Unit = this.introduce_factory(null)
	def introduce_factory(cls: RSClass = null): Unit = {
		self match{
			case c: RSClass => {
				println("RSClass")
				val processor = new RSIntroduceFactoryRefactoringProcessor(c, cls)
				processor.createAction().perform()
			}
			// コンストラクタが直接来た時
			case m: RSMethod => {
				println("RSMethod")
				val processor = new RSIntroduceFactoryRefactoringProcessor(m, cls)
				processor.createAction().perform()
			}
			case _ => println("other")
			
		}
		println("introduce factory was completed")
	}

}