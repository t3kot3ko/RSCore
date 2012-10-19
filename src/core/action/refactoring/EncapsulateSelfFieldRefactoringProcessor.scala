package core.action.refactoring
import dsl.target.RSTargetCollection
import dsl.entity.RSField
import org.eclipse.jdt.core.IField
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.jdt.internal.corext.refactoring.sef.SelfEncapsulateFieldRefactoring
import org.eclipse.ltk.core.refactoring.Change

class EncapsulateSelfFieldRefactoringProcessor(val targets: RSTargetCollection) extends AbstractRefactoringProcessor {
	def createAction(): RSRefactoringAction = {
		if (!validTargets) {
			return new RSRefactoringAction(Seq())
		}
		def actions(): Unit = {
			targets.toArray().foreach(
				target =>
					createActionElement(target.entity.asInstanceOf[RSField]).perform(new NullProgressMonitor))
		}
		
		val a = actions _
		return new RSRefactoringAction(Array(a))
	}

	private def createActionElement(f: RSField): Change = {
		var field: IField = f.origin()
		var pm = new NullProgressMonitor
		var refactoring = new SelfEncapsulateFieldRefactoring(field)

		var initialStatus = refactoring.checkInitialConditions(pm)
		var finalStatus = refactoring.checkFinalConditions(pm)
		if (!initialStatus.isOK() || !finalStatus.isOK()) {
			return null
		}

		var change: Change = refactoring.createChange(pm)
		return change
	}

	private def validTargets(): Boolean = {
		return checkerBuilder(
			this.targets.toArray().forall(t => t.entity.__identifier == "field"),
			this.targets.toArray().forall(t => t.entity.__identifier == "field"))
	}

	// •¡”‚Ì Boolean ‚É‘Î‚µ‚Ä AND ‚ðŽæ‚é‚¾‚¯
	private def checkerBuilder(checkers: Boolean*): Boolean = {
		return checkers.forall(e => e)
	}

}