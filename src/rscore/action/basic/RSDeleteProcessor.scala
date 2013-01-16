package rscore.action.basic
import rscore.action.RSAbstractActionProcessor
import rscore.dsl.common.RSObject
import org.eclipse.jdt.internal.corext.refactoring.reorg.JavaDeleteProcessor
import rscore.dsl.entity.RSEntity
import rscore.action.basic.RSBasicAction
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import rscore.helper.RefactoringHelper
import rscore.dsl.entity.RSMethod
import org.eclipse.jdt.core.IMember
import rscore.dsl.entity.RSMember

class RSDeleteProcessor(rsObject: RSObject) extends RSAbstractActionProcessor{
	override def createAction(): RSBasicAction = {
		rsObject match {
			case m: RSMember=> return createActionForMember(m)
		}
		return new RSBasicAction(Seq())
		
	}
	
	private def createActionForMember(member: RSMember): RSBasicAction = {
		val action: (() => Unit) = 
			() => {
				val o = member.origin()
				o.asInstanceOf[IMember].delete(true, null)
			}
		return new RSBasicAction(Seq(action))
		
	}

}