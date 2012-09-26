package core.sandbox
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.jdt.core.IType
import org.eclipse.jdt.core.IMember
import org.eclipse.jdt.internal.corext.refactoring.structure.MoveStaticMembersProcessor
import org.eclipse.ltk.core.refactoring.participants.MoveRefactoring
import core.helper.RefactoringHelper
import org.eclipse.jdt.internal.ui.preferences.JavaPreferencesSettings

object MoveMethod {
	def moveMethodSample(unit: ICompilationUnit, destination: String): Unit = {
		var typ: IType = unit.getType("MoveMethod")
		var member: IMember = typ.getField("staticField").asInstanceOf[IMember]

		// これ必要（たぶん，対象プロジェクトを教えてあげる必要がある）
		var project = member.getJavaProject()
		var processor = new MoveStaticMembersProcessor(Array[IMember](member), JavaPreferencesSettings.getCodeGenerationSettings(project))
		processor.setDestinationTypeFullyQualifiedName(destination)

		var refactoring: MoveRefactoring = new MoveRefactoring(processor)
		RefactoringHelper.performRefactoring(refactoring)

	}

}