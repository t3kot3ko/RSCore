package rscore.helper
import org.eclipse.ltk.core.refactoring.CheckConditionsOperation
import org.eclipse.ltk.core.refactoring.CreateChangeOperation
import org.eclipse.ltk.core.refactoring.RefactoringStatus
import org.eclipse.ltk.core.refactoring.PerformChangeOperation
import org.eclipse.ltk.core.refactoring.Refactoring
import org.eclipse.ltk.core.refactoring.RefactoringCore
import org.eclipse.ltk.core.refactoring.IUndoManager
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.ltk.core.refactoring.Change

object RefactoringHelper {
	/**
	 * ProcessorBasedRefactoring Çé¿çsÇ∑ÇÈÇΩÇﬂÇÃàÍòAÇÃéËë±Ç´
	 */
	def performRefactoring(ref: Refactoring): RSRefactoringResult = {
		var check = new CheckConditionsOperation(ref, CheckConditionsOperation.ALL_CONDITIONS)
		var create: CreateChangeOperation = new CreateChangeOperation(check, RefactoringStatus.FATAL)
		var perform: PerformChangeOperation = new PerformChangeOperation(create)
		
		var undoManager: IUndoManager = RefactoringCore.getUndoManager()
		perform.setUndoManager(undoManager, ref.getName())
		
		ResourcesPlugin.getWorkspace().run(perform, new NullProgressMonitor())
		
		var status: RefactoringStatus = create.getConditionCheckingStatus()
		
		if (!status.isOK()) {
			println(status)
			throw new RefactoringExecutionException(status)
		}
		var undo: Change = perform.getUndoChange()
		return new RSRefactoringResult(status, undo)
	}
}

class RefactoringExecutionException(status: RefactoringStatus) extends Exception("Refactoring hasn't been executed succesfully"){
	def getStatus(): RefactoringStatus = status
}

class RSRefactoringResult(status: RefactoringStatus, undo: Change){
	def getStatus(): RefactoringStatus = status
	def getUndo(): Change = undo
}