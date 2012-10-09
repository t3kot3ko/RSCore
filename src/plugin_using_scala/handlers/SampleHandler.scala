package plugin_using_scala.handlers;

import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.ui.IWorkbenchWindow
import org.eclipse.ui.handlers.HandlerUtil
import org.eclipse.jface.dialogs.MessageDialog
import org.eclipse.jface.viewers.ISelection
import org.eclipse.jface.viewers.IStructuredContentProvider
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.jdt.core.IType
import org.eclipse.jdt.core.IJavaElement
import org.eclipse.jdt.core.IField
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.jdt.internal.corext.refactoring.sef.SelfEncapsulateFieldRefactoring
import org.eclipse.ltk.core.refactoring.RefactoringStatus
import org.eclipse.ltk.core.refactoring.Change
import org.eclipse.jdt.internal.corext.refactoring.surround.SurroundWithTryCatchRefactoring
import org.eclipse.jdt.internal.corext.refactoring.code.ExtractMethodRefactoring
import org.eclipse.jface.text.TextSelection
import org.eclipse.jdt.internal.corext.refactoring.code.ExtractTempRefactoring
import org.eclipse.jdt.internal.corext.refactoring.structure.ChangeTypeRefactoring
import org.eclipse.jdt.internal.corext.refactoring.code.ExtractConstantRefactoring
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameFieldProcessor
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring
import org.eclipse.ltk.core.refactoring.CreateChangeOperation
import org.eclipse.ltk.core.refactoring.CheckConditionsOperation
import org.eclipse.ltk.core.refactoring.PerformChangeOperation
import core.sandbox.RenameField
import org.eclipse.jdt.core.Signature
import org.eclipse.jdt.core.IMethod
import org.eclipse.ltk.core.refactoring.participants.RenameProcessor
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameMethodProcessor
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameVirtualMethodProcessor
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.ltk.core.refactoring.RefactoringCore
import core.helper.SelectionHelper
import org.eclipse.jface.text.ITextSelection
import core.sandbox.ExtractMethod
import org.eclipse.jdt.internal.corext.refactoring.structure.PullUpRefactoringProcessor
import org.eclipse.jdt.core.IMember
import org.eclipse.jdt.internal.corext.refactoring.util.JavaElementUtil
import scala.collection.mutable.HashSet
import org.eclipse.jdt.internal.ui.preferences.JavaPreferencesSettings
import org.eclipse.ltk.core.refactoring.Refactoring
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import org.eclipse.jdt.internal.corext.refactoring.RefactoringAvailabilityTester
import core.sandbox.PullUp
import org.eclipse.jdt.internal.corext.refactoring.structure.PushDownRefactoringProcessor
import org.eclipse.ltk.core.refactoring.participants.MoveRefactoring
import org.eclipse.jdt.internal.corext.refactoring.structure.MoveStaticMembersProcessor
import core.helper.RefactoringHelper
import core.sandbox.MoveMethod
import org.eclipse.jdt.internal.corext.refactoring.code.IntroduceFactoryRefactoring
import core.sandbox.IntroduceFactory
import core.helper.CUHelper
import org.eclipse.jdt.core.dom.ASTParser
import org.eclipse.jdt.core.dom.AST
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.core.dom.TypeDeclaration
import scala.collection.JavaConverters._
import org.eclipse.jdt.core.dom.FieldDeclaration
import org.eclipse.jdt.core.dom.Modifier
import org.eclipse.jdt.core.dom.VariableDeclarationFragment
import scala.collection.mutable.Buffer
import org.eclipse.jdt.internal.corext.refactoring.structure.ChangeSignatureProcessor
import org.eclipse.jdt.internal.corext.refactoring.structure.BodyUpdater
import org.eclipse.jdt.internal.corext.refactoring.ParameterInfo
import java.util.ArrayList
import dsl.entity.RSClass
import dsl.entity.collection.RSMethods._
import dsl.entity.collection.RSFields._
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
import dsl.util.ASTUtil
import application.sample.RenameMultipleFields
import dsl.common.RSParams
import dsl.entity.RSPackage

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
class SampleHandler extends AbstractHandler {

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	def execute(event: ExecutionEvent): Object = {
		println("execute() is invoked")
		var window: IWorkbenchWindow = HandlerUtil.getActiveWorkbenchWindowChecked(event);

		var projectName = "Sample"
		var packageName = "rename"
		var unitName = "RenameField"
		var project = CUHelper.getJavaProject(projectName)
		var root = CUHelper.getSourceFolder(project)
		var pack = root.getPackageFragment(packageName)
		
		var unit = CUHelper.getCompilationUnit(pack, unitName)
		
		// assert(unit == null)
		
		var typ = unit.getType(unitName)
		var $ = new RSClass(typ)
		
		var privateMethods = $.methods.where(RSParams("return" -> Array("int", "void")))
		var nameMatchedMethods = $.methods.where(RSParams("name" -> Array("public3", "publicstatic")))
		var regMatchedMethods = $.methods.where(RSParams("namereg" -> Array(""".*\d$"""), "modifier" -> Array("protected")))
		
		// var privateMethods = $.methods.where(RSParams("modifier" -> Array("private")))
		// var privateMethods = $.methods.where(RSParams("return" -> Array("private"), "modifier" -> Array("private", "public")))
		println("private method count = " + privateMethods.length)
		println("name matched count = " + nameMatchedMethods.length)
		println("name matched count = " + regMatchedMethods.length)
		
		var privateFields = $.fields.where(RSParams("modifier" -> Array("final"))).where(RSParams("namereg" -> Array("""[a-z].*""")))
		
		// regMatchedMethods.foreach(e => println(e.name))
		privateFields.foreach(e => println(e.name))
		
		/*
		println("private method count = " + $.fields.where("private").length)
		var privateMethodNames = $.fields.where("private").map(e => e.name)
		RenameMultipleFields.renamePrivateFieldWithUnderscoreSample(privateMethodNames, unit)
		*/
		

		/*
		var typ = CUHelper.findTargetType("Sample", "extract_constant", "Foo")
		var cu = typ.getCompilationUnit()
		
		var source = typ.getSource()
		var range = SelectionHelper.getSelection(source)
		var refactoring = new ExtractConstantRefactoring(cu, range(0), range(1))
		
		refactoring.setConstantName("CONSTANT")
		refactoring.setReplaceAllOccurrences(true)
		refactoring.setQualifyReferencesWithDeclaringClassName(true)
		
		var pm = new NullProgressMonitor()
		var initialStatus = refactoring.checkInitialConditions(pm)
		println(initialStatus)
		
		assert(initialStatus.isOK())
		// assert(finalStatus.isOK())
		
		var change = refactoring.createChange(pm)
		var undo = change.perform(pm)
		
		change.dispose()
		*/

		alert(window, "Complete", "execute() has been successfully executed")
		return null

		var selection: ISelection = HandlerUtil.getCurrentSelection(event)

		println(selection.getClass().toString())

		if (selection.isInstanceOf[TextSelection]) {
			var tSelection = selection.asInstanceOf[TextSelection]
			println("offset =" + tSelection.getOffset())
			println("length =" + tSelection.getLength())
			println("startline = " + tSelection.getStartLine())
			println("endline= " + tSelection.getEndLine())
			var text = tSelection.getText()
		} else if (selection.isInstanceOf[IStructuredSelection]) {
			var sSelection = selection.asInstanceOf[IStructuredSelection]
			var firstElement = sSelection.getFirstElement()
			if (firstElement.isInstanceOf[ICompilationUnit]) {
				var unit: ICompilationUnit = firstElement.asInstanceOf[ICompilationUnit]
			}
		}

		MessageDialog.openInformation(
			window.getShell(),
			"Test Plugins",
			"Hello, Eclipse world");
		return null;
	}

	private def alert(window: IWorkbenchWindow, title: String, message: String): Unit = {
		MessageDialog.openInformation(window.getShell(), title, message)
	}

	private def merge(a: Array[IMember], b: Array[IMember]): Array[IMember] = {
		return JavaElementUtil.merge(a, b)
	}

}

