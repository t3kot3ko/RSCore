package dsl.target
import core.action.refactoring.EncapsulateSelfFieldRefactoringProcessor

/**
 * RSTarget ‚ÌW‡i‚½‚¾‚»‚ê‚¾‚¯j
 */
class RSTargetCollection(val targets: Array[RSTarget]) {
	def this(targets: RSTarget*) = this(targets.toArray[RSTarget])
	
	/**
	 * TODO: ‚à‚¤‚¢‚ç‚È‚¢‚Ì‚ÅÁ‚·
	 */
	@Deprecated
	def toArray() : Array[RSTarget] = {
		return targets
	}
	
	def perform(actionId: String): Unit = {
		actionId match {
			case "sef" => new EncapsulateSelfFieldRefactoringProcessor(this).createAction().perform()
		}
	}

}