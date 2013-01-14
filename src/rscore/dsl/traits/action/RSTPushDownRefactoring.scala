package rscore.dsl.traits.action
import rscore.action.refactoring.RSPushDownRefactoringProcessor

trait RSTPushDownRefactoring extends RefactoringTrait {
	def pushDown(): Unit = {
		val processor = new RSPushDownRefactoringProcessor(self)
		processor.createAction().perform()
	}
	
	def push_down(): Unit = this.pushDown()	// just an alias

}