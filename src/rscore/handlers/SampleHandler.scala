package rscore.handlers;

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
import rscore.sandbox.RenameField
import org.eclipse.jdt.core.Signature
import org.eclipse.jdt.core.IMethod
import org.eclipse.ltk.core.refactoring.participants.RenameProcessor
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameMethodProcessor
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameVirtualMethodProcessor
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.ltk.core.refactoring.RefactoringCore
import rscore.helper.SelectionHelper
import org.eclipse.jface.text.ITextSelection
import rscore.sandbox.ExtractMethod
import org.eclipse.jdt.internal.corext.refactoring.structure.PullUpRefactoringProcessor
import org.eclipse.jdt.core.IMember
import org.eclipse.jdt.internal.corext.refactoring.util.JavaElementUtil
import scala.collection.mutable.HashSet
import org.eclipse.jdt.internal.ui.preferences.JavaPreferencesSettings
import org.eclipse.ltk.core.refactoring.Refactoring
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import org.eclipse.jdt.internal.corext.refactoring.RefactoringAvailabilityTester
import rscore.sandbox.PullUp
import org.eclipse.jdt.internal.corext.refactoring.structure.PushDownRefactoringProcessor
import org.eclipse.ltk.core.refactoring.participants.MoveRefactoring
import org.eclipse.jdt.internal.corext.refactoring.structure.MoveStaticMembersProcessor
import rscore.helper.RefactoringHelper
import rscore.sandbox.MoveMethod
import org.eclipse.jdt.internal.corext.refactoring.code.IntroduceFactoryRefactoring
import rscore.sandbox.IntroduceFactory
import rscore.helper.CUHelper
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
import rscore.dsl.entity.RSClass
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
import rscore.dsl.util.ASTUtil
import rscore.application.sample.RenameMultipleFields
import rscore.dsl.entity.RSPackage
import rscore.dsl.common.RSParam
import scala.util.matching.Regex
import rscore.dsl.common.RSParam._
import com.sun.xml.internal.ws.wsdl.writer.document.xsd.Import
import rscore.dsl.entity.RSProject
import rscore.dsl.entity.RSWorkspace
import rscore.dsl.util.ImplicitConversions._
import rscore.dsl.entity.RSMethod
import rscore.dsl.entity.collection.By
import rscore.dsl.entity.collection.With
import rscore.action.refactoring.SelfEncapsulateFieldRefactoringProcessor
import rscore.dsl.entity.RSField
import rscore.dsl.common.RSObject
import rscore.action.refactoring.SelfEncapsulateFieldRefactoringProcessor
import rscore.action.refactoring.RSRefactoringAction

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

		var $ = RSWorkspace
		// $.refresh()
		// $.project("Sample").pkg("introduce_factory").classes.first.introduce_factory
		
		// rename private fields adding prefix
		/*
		val privateFields = $.project("Sample").pkg("test.dsl").classes.first.fields.select(By.Modifier(With.or(Array("private"))))
		.foreach(e => e.rename("_" + e.name))
		*/
		println("before")
		$.project("Sample").pkg("test.dsl").classes.first.constructors.first.introduce_factory()
		println("after")
		
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


