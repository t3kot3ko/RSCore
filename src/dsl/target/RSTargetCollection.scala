package dsl.target
import core.action.refactoring.EncapsulateSelfFieldRefactoringProcessor

/**
 * RSTarget の集合（ただそれだけ）
 */
class RSTargetCollection(val targets: Array[RSTarget]) {
	def this(targets: RSTarget*) = this(targets.toArray[RSTarget])
	
	/**
	 * TODO: もういらないので消す
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