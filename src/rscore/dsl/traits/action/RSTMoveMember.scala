package rscore.dsl.traits.action
import rscore.dsl.entity.RSClass
import rscore.action.basic.MoveMemberProcessor

trait RSTMoveMember extends RefactoringTrait{
	def moveMember(to: RSClass): Unit = {
		val processor = new MoveMemberProcessor(self, to)
		processor.createAction().perform()
	}

}