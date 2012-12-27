package rscore.action.basic
import rscore.action.AbstractActionProcessor
import rscore.dsl.common.RSObject
import rscore.dsl.entity.RSMember
import rscore.dsl.entity.RSClass
import org.eclipse.jdt.internal.corext.refactoring.structure.MoveStaticMembersProcessor
import org.eclipse.jdt.core.IMember
import rscore.dsl.entity.collection.RSCollection
import org.eclipse.jdt.internal.ui.preferences.JavaPreferencesSettings
import org.eclipse.ltk.core.refactoring.participants.MoveRefactoring
import rscore.helper.RefactoringHelper

class MoveMemberProcessor(rsObject: RSObject, destClass: RSClass) extends AbstractActionProcessor {
	override def createAction(): RSBasicAction = {
		rsObject match {
			case m: RSMember => return createActionForMembers(Array(m.origin))
			case c: RSCollection[_] => {
				if (c.all().forall(_.isInstanceOf[RSMember])) {
					println("For members collection")
					return createActionForMembers(c.all.map(_.asInstanceOf[RSMember].origin))
				}
			}

		}

		println("Not member")
		return new RSBasicAction(Seq())

	}

	private def createActionForMembers(members: Array[IMember]): RSBasicAction = {
		val action: (() => Unit) =
			() =>
				{
					if (members.length > 0) {
						val project = members.first.getJavaProject()
						val processor = new MoveStaticMembersProcessor(
							members,
							JavaPreferencesSettings.getCodeGenerationSettings(project))

						println(destClass.origin().getFullyQualifiedName())
						processor.setDestinationTypeFullyQualifiedName(destClass.origin().getFullyQualifiedName())

						val ref = new MoveRefactoring(processor)
						val result = RefactoringHelper.performRefactoring(ref)
					}
					else{
						
					}
				}

		return new RSBasicAction(Seq(action))
	}

}