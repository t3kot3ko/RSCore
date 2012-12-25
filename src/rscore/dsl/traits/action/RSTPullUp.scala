package rscore.dsl.traits.action
import rscore.dsl.entity.RSClass
import rscore.action.refactoring.PullUpRefactoringProcessor

trait RSTPullUp extends RefactoringTrait{
	def pullUp(targetClass: RSClass): Unit = {
		val processor = new PullUpRefactoringProcessor(self, targetClass)
		processor.createAction().perform()
	}
	

}