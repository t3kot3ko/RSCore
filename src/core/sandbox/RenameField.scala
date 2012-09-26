package core.sandbox

import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.jdt.core.IType
import org.eclipse.jdt.core.IField
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameFieldProcessor
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.ltk.core.refactoring.RefactoringStatus
import org.eclipse.ltk.core.refactoring.Change
import org.eclipse.ltk.core.refactoring.CreateChangeOperation
import org.eclipse.ltk.core.refactoring.CreateChangeOperation
import org.eclipse.ltk.core.refactoring.PerformChangeOperation
import org.eclipse.ltk.core.refactoring.CheckConditionsOperation
import org.eclipse.core.resources.ResourcesPlugin
import core.helper.RefactoringHelper

object RenameField {
	// äÓñ{ìIÇ…ÇÕÇ±Ç¡ÇøÇÃï˚êjÇ≈ÅI
	def renameFieldRefactoringSample2(unit: ICompilationUnit, targetFieldName: String, newFieldName: String): Unit = {
		var foo: IType = unit.getType("RenameField")
		var field: IField = foo.getField(targetFieldName)

		var processor: RenameFieldProcessor = new RenameFieldProcessor(field)
		processor.setNewElementName(newFieldName)

		var refactoring: RenameRefactoring = new RenameRefactoring(processor)
		println("PERFORMING")
		var status = RefactoringHelper.performRefactoring(refactoring)
		if(status != null){
			println(status)
		}
	}

	def renameFieldRefactoringSample(unit: ICompilationUnit, targetFieldName: String, newFieldName: String): Unit = {

		var foo: IType = unit.getType("RenameField")
		var field: IField = foo.getField(targetFieldName)

		var processor: RenameFieldProcessor = new RenameFieldProcessor(field)
		processor.setNewElementName(newFieldName)

		var refactoring: RenameRefactoring = new RenameRefactoring(processor)
		var pm = new NullProgressMonitor()
		var initialCondition: RefactoringStatus = refactoring.checkInitialConditions(pm)
		var finalCondition: RefactoringStatus = refactoring.checkFinalConditions(pm)

		println(initialCondition.toString())
		println(finalCondition.toString())

		var change: Change = refactoring.createChange(null)

		var undo = change.perform(pm)
		change.dispose()
		// undo.perform(pm)
		// undo.dispose()

	}

}