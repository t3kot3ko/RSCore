package core.action.basic
import core.action.AbstractActionProcessor
import dsl.common.RSObject
import org.eclipse.jdt.internal.corext.refactoring.reorg.JavaDeleteProcessor
import dsl.entity.RSEntity
import core.action.basic.BasicAction
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import core.helper.RefactoringHelper
import dsl.entity.RSMethod
import org.eclipse.jdt.core.IMember
import dsl.entity.RSMember

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