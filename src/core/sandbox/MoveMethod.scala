package core.sandbox
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.jdt.core.IType
import org.eclipse.jdt.core.IMember
import org.eclipse.jdt.internal.corext.refactoring.structure.MoveStaticMembersProcessor
import org.eclipse.ltk.core.refactoring.participants.MoveRefactoring
import core.helper.RefactoringHelper
import org.eclipse.jdt.internal.ui.preferences.JavaPreferencesSettings
import org.eclipse.jdt.core.Signature
import org.eclipse.jdt.internal.corext.refactoring.util.JavaElementUtil
import org.eclipse.jdt.internal.corext.refactoring.structure.MoveInstanceMethodProcessor

object MoveMethod {
	def moveMethodSample(unit: ICompilationUnit, destination: String): Unit = {
		var typ: IType = unit.getType("MoveMethod")

		// var member: IMember = typ.getField("staticField").asInstanceOf[IMember]
		var fields: Array[IMember] = Array[IMember](
			typ.getField("staticField").asInstanceOf[IMember],
			typ.getField("instanceField").asInstanceOf[IMember])
		var staticMethod = typ.getMethod("staticMethod", Array(Signature.SIG_INT, Signature.SIG_INT))

		var methods: Array[IMember] = Array(staticMethod).map(e => e.asInstanceOf[IMember])
		var members = JavaElementUtil.merge(fields, methods)

		assert(staticMethod != null)

		// これ必要（たぶん，対象プロジェクトを教えてあげる必要がある）
		var project = members(0).getJavaProject()
		// var processor = new MoveStaticMembersProcessor(Array[IMember](member), JavaPreferencesSettings.getCodeGenerationSettings(project))
		// var processor = new MoveStaticMembersProcessor(fields, JavaPreferencesSettings.getCodeGenerationSettings(project))
		var processor = new MoveStaticMembersProcessor(members, JavaPreferencesSettings.getCodeGenerationSettings(project))

		processor.setDestinationTypeFullyQualifiedName(destination)

		var refactoring: MoveRefactoring = new MoveRefactoring(processor)
		RefactoringHelper.performRefactoring(refactoring)
	}

	// TODO: インスタンスメソッドを移動できない
	def moveInstanceMethodSample(unit: ICompilationUnit, destination: String): Unit = {
		var typ: IType = unit.getType("MoveMethod")
		var instanceMethod = typ.getMethod("instanceMethod", Array(Signature.SIG_VOID))
		assert(instanceMethod != null)
		
		var project = instanceMethod.getJavaProject()
		var processor = new MoveInstanceMethodProcessor(instanceMethod, JavaPreferencesSettings.getCodeGenerationSettings(project))
		var ref = new MoveRefactoring(processor)
		RefactoringHelper.performRefactoring(ref)
		

	}

}