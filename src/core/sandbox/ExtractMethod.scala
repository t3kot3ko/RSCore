package core.sandbox
import org.eclipse.jdt.core.ICompilationUnit
import core.helper.SelectionHelper
import org.eclipse.jdt.internal.corext.refactoring.code.ExtractMethodRefactoring
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.ltk.core.refactoring.Change

object ExtractMethod {
	def extractMethodSample(unit: ICompilationUnit): Unit = {
		var source: String = unit.getSource()
		var selection = SelectionHelper.getSelection(source)
		var refactoring: ExtractMethodRefactoring = new ExtractMethodRefactoring(unit, selection(0), selection(1))

		var pm = new NullProgressMonitor()
		var initialStatus = refactoring.checkInitialConditions(pm)
		var finalStatus = refactoring.checkFinalConditions(pm)

		println(initialStatus)
		println(finalStatus)

		var change: Change = refactoring.createChange(pm)
		var undo: Change = change.perform(pm)
		change.dispose()

	}

}