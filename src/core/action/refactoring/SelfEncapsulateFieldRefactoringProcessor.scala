package core.action.refactoring
import dsl.entity.RSField
import org.eclipse.jdt.core.IField
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.jdt.internal.corext.refactoring.sef.SelfEncapsulateFieldRefactoring
import org.eclipse.ltk.core.refactoring.Change
import dsl.util.ImplicitConversions._
import dsl.common.RSObject
import dsl.entity.collection.RSFields

object SelfEncapsulateFieldRefactoringProcessor {
	def createAction(rsField: RSObject): RSRefactoringAction = {
		rsField match {
			case f: RSField => createActionForEntity(f)
			case fs: RSFields => createActionForCollection(fs)
		}
	}

	private def createActionForEntity(rsField: RSField): RSRefactoringAction = {
		val action = a(rsField)
		return new RSRefactoringAction(Seq(action))
	}

	private def createActionForCollection(rsFields: RSFields): RSRefactoringAction = {
		val ary = rsFields.all()
		val actions = ary.toSeq.map(e => a(e))
		return new RSRefactoringAction(actions)
	}

	def a(f: RSField): (() => Unit) = {
		val action =
			() => {
				val field: IField = f.origin()
				var pm = new NullProgressMonitor
				var refactoring = new SelfEncapsulateFieldRefactoring(field)

				var initialStatus = refactoring.checkInitialConditions(pm)
				var finalStatus = refactoring.checkFinalConditions(pm)
				if (!initialStatus.isOK() || !finalStatus.isOK()) {
					return null
				}

				var change: Change = refactoring.createChange(pm)
				change.perform(pm)

				return null // TODO: refactor to deal undo changes
			}
		return action
	}

}