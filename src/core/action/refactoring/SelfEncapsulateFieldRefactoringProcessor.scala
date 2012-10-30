package core.action.refactoring
import dsl.entity.RSField
import org.eclipse.jdt.core.IField
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.jdt.internal.corext.refactoring.sef.SelfEncapsulateFieldRefactoring
import org.eclipse.ltk.core.refactoring.Change
import dsl.util.ImplicitConversions._
import dsl.common.RSObject
import dsl.entity.collection.RSFields

class SelfEncapsulateFieldRefactoringProcessor(rsField: RSObject) extends AbstractRefactoringProcessor {
	// def createAction(rsField: RSObject): RSRefactoringAction = {
	override def createAction(): RSRefactoringAction = {
		rsField match {
			case f: RSField => return createActionForEntity(f)
			case fs: RSFields => return createActionForCollection(fs)
		}
		return new RSRefactoringAction(Seq())
	}

	private def createActionForEntity(rsField: RSField): RSRefactoringAction = {
		val action = a(rsField)
		return RSRefactoringAction(Seq(action))
	}

	private def createActionForCollection(rsFields: RSFields): RSRefactoringAction = {
		val ary = rsFields.all()
		val actions = ary.map(e => a(e))
		return RSRefactoringAction(actions)
	}

	// TODO: rename to an appropreate name
	def a(f: RSField): () => Unit = {
		val action: (() => Unit)=
			() => {
				val field: IField = f.origin()
				var pm = new NullProgressMonitor
				var refactoring = new SelfEncapsulateFieldRefactoring(field)

				var initialStatus = refactoring.checkInitialConditions(pm)
				var finalStatus = refactoring.checkFinalConditions(pm)
				if (!initialStatus.isOK() || !finalStatus.isOK()) {
					throw new Exception("Condition checking exception")
				}

				var change: Change = refactoring.createChange(pm)
				change.perform(pm)
			}
		return action
	}

}