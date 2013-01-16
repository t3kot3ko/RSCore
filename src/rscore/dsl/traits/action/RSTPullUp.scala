package rscore.dsl.traits.action
import rscore.dsl.entity.RSClass
import rscore.action.refactoring.RSPullUpRefactoringProcessor

trait RSTPullUp extends RefactoringTrait{
	def pullUp(targetClass: RSClass): Unit = {
		val processor = new RSPullUpRefactoringProcessor(self, targetClass)
		processor.createAction().perform()
	}
	

}