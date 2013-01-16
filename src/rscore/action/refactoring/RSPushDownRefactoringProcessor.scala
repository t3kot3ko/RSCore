package rscore.action.refactoring
import rscore.dsl.common.RSObject
import rscore.helper.RefactoringHelper
import org.eclipse.jdt.internal.corext.refactoring.RefactoringAvailabilityTester
import org.eclipse.jdt.core.IMember
import rscore.dsl.entity.RSMember
import rscore.dsl.entity.collection.RSCollection
import org.eclipse.jdt.internal.corext.refactoring.structure.PushDownRefactoringProcessor
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import rscore.helper.RefactoringHelper

class RSPushDownRefactoringProcessor(rsObject: RSObject) extends RSAbstractRefactoringProcessor {
	override def createAction(): RSRefactoringAction = {
		rsObject match {
			case m: RSMember => createActionForMembers(Array(m))
			case ms: RSCollection[RSMember] => createActionForMembers(ms.toArray)
		}
	}

	private def createActionForMembers(ms: Array[RSMember]): RSRefactoringAction = {
		val action: (() => Unit) =
			() =>
				{
					val members: Array[IMember] = ms.map(_.origin)
					if (!RefactoringAvailabilityTester.isPushDownAvailable(members)) {

					}
					val processor = new PushDownRefactoringProcessor(members)
					val refactoring = new ProcessorBasedRefactoring(processor)

					RefactoringHelper.performRefactoring(refactoring)

				}
		return new RSRefactoringAction(Seq(action))
	}

}