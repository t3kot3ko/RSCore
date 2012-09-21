package core.sandbox
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.jdt.core.IType
import org.eclipse.jdt.core.IMethod
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameMethodProcessor
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameVirtualMethodProcessor
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring
import org.eclipse.ltk.core.refactoring.CheckConditionsOperation
import org.eclipse.ltk.core.refactoring.CreateChangeOperation
import org.eclipse.ltk.core.refactoring.PerformChangeOperation
import org.eclipse.ltk.core.refactoring.RefactoringCore
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.ltk.core.refactoring.RefactoringStatus
import org.eclipse.jdt.core.Signature

object RenameMethod {
	def renameStaticMethodSample(unit: ICompilationUnit): Unit = {
		var t: IType = unit.getType("RenameStaticMethod")
		println(t.exists())

		var method: IMethod = t.getMethod("mmm", Array[String](Signature.SIG_INT))

		var pm = new NullProgressMonitor()
		var processor: RenameMethodProcessor = new RenameVirtualMethodProcessor(method)
		processor.setNewElementName("k")
		processor.setUpdateReferences(true)

		var refactoring = new RenameRefactoring(processor)
		/*
				var initialStatus: RefactoringStatus = refactoring.checkInitialConditions(pm)
				println(initialStatus)
				
				var finalStats: RefactoringStatus = refactoring.checkFinalConditions(pm)
				println(finalStats)
				*/

		var check: CheckConditionsOperation = new CheckConditionsOperation(refactoring, CheckConditionsOperation.INITIAL_CONDITONS)
		assert(check != null)

		var create = new CreateChangeOperation(check, RefactoringStatus.ERROR)
		assert(create != null)
		var perform = new PerformChangeOperation(create)
		var undoManager = RefactoringCore.getUndoManager()

		perform.setUndoManager(undoManager, refactoring.getName())
		ResourcesPlugin.getWorkspace().run(perform, pm)
		perform.run(pm)

		var status: RefactoringStatus = create.getConditionCheckingStatus()
		assert(status != null)

		if (status.isOK()) {
			println("ok")
		} else {
			println("ng")
			println(status)
		}

		/*
				
				var perform = new PerformChangeOperation(create)
				var change : Change = perform.getChange()
				change.perform(pm)
				*/

		// var change: Change = refactoring.createChange(pm)
		/*
				var undo: Change = change.perform(pm)
				change.dispose()
				*/

	}

}