package rscore.action.basic
import rscore.action.AbstractActionProcessor
import rscore.dsl.common.RSObject
import org.eclipse.jdt.internal.corext.refactoring.reorg.JavaDeleteProcessor
import rscore.dsl.entity.RSEntity
import rscore.action.basic.BasicAction
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import rscore.helper.RefactoringHelper
import rscore.dsl.entity.RSMethod
import org.eclipse.jdt.core.IMember
import rscore.dsl.entity.RSMember

class DeleteProcessor(rsObject: RSObject) extends AbstractActionProcessor{
	override def createAction(): BasicAction = {
		rsObject match {
			case m: RSMember=> return createActionForMember(m)
		}
		return new BasicAction(Seq())
		
	}
	
	private def createActionForMember(member: RSMember): BasicAction = {
		val action: (() => Unit) = 
			() => {
				val o = member.origin()
				o.asInstanceOf[IMember].delete(true, null)
			}
		return new BasicAction(Seq(action))
		
	}

}