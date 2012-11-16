package dsl.traits.action
import core.action.refactoring.AbstractRefactoringProcessor
import core.action.refactoring.RSRefactoringAction
import dsl.entity.RSClass
import core.action.refactoring.IntroduceFactoryRefactoringProcessor
import dsl.entity.RSMethod

trait RSTIntroduceFactory extends RefactoringTrait{
	def introduce_factory(): Unit = {
		self match{
			case c: RSClass => {
				println("RSClass")
				val processor = new IntroduceFactoryRefactoringProcessor(c)
				processor.createAction().perform()
			}
			// コンストラクタが直接来た時
			case m: RSMethod => {
				println("RSMethod")
				val processor = new IntroduceFactoryRefactoringProcessor(m)
				processor.createAction().perform()
			}
			case _ => println("other")
			
		}
		println("introduce factory was completed")
	}

}