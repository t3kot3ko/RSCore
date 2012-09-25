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
		var window: IWorkbenchWindow = HandlerUtil.getActiveWorkbenchWindowChecked(event);
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
				PullUp.pullUpSample(unit)
				/*
				// RenameField.renameFieldRefactoringSample2(unit, "_a", "_b")
				// ExtractMethod.extractMethodSample(unit)
				var typ = unit.getType("Bar")
				/*
				var methodNames = typ.getMethods().map(e => e.getElementName())
				var methodSignatures = typ.getMethods().map(e => Array[String](e.getSignature()))
				var memberMethods = getMethods(typ, methodNames, methodSignatures)
				assert(memberMethods != null)
				
				var fieldNames = typ.getFields().map(e => e.getElementName())
				var memberFields = getFields(typ, fieldNames)
				assert(memberFields != null)
				
				var members: Array[IMember] = merge(memberMethods.asInstanceOf[Array[IMember]], memberFields.asInstanceOf[Array[IMember]])
				assert(members != null)
				println(members.length);
				*/
				var members : Array[IMember] = typ.getFields().map(e => e.asInstanceOf[IMember])
				println(members.length)
				
				println("########################")
				println(RefactoringAvailabilityTester.isPullUpAvailable(members))
				
				var project = members(0).getJavaProject()
				assert(project != null)
				var processor = new PullUpRefactoringProcessor(members, JavaPreferencesSettings.getCodeGenerationSettings(project))
				new ProcessorBasedRefactoring(processor)
				var refactoring:Refactoring = processor.getRefactoring()
				// var refactoring = new ProcessorBasedRefactoring(processor)
				
				assert(refactoring != null)
				
				var pm = new NullProgressMonitor()
				var initialStatus = refactoring.checkInitialConditions(pm)
				println(initialStatus.isOK())
				
				setTargetClass(processor, 0)
				
				assert(processor.getDestinationType() != null)
				
				assert(refactoring != null)
				var finalStatus = refactoring.checkFinalConditions(new NullProgressMonitor())
				println(finalStatus)
				
				var change: Change = refactoring.createChange(pm)
				var undo:Change = change.perform(pm)
				
				undo.perform(pm)
				*/
				

			}
		}

		MessageDialog.openInformation(
			window.getShell(),
			"Test Plugins",
			"Hello, Eclipse world");
		return null;
	}

	// TODO: extract into helper class
	/**
	 * 2つの Array[IMember] をマージして1つにする
	 */
	def merge(a: Array[IMember], b: Array[IMember]): Array[IMember] = {
		return JavaElementUtil.merge(a, b)
	}

	// typ 以下のフィールドを取ってくる．
	// IType#getType のラッパ
	def getFields(typ: IType, names: Array[String]): Array[IField] = {
		if (names == null) {
			return new Array[IField](0);
		}
		var fields: HashSet[IField] = new HashSet[IField]()
		for (i <- 0 until names.length) {
			var field = typ.getField(names(i))
			fields.add(field)
		}
		return fields.toArray[IField]
	}
	def getMethods(typ: IType, names: Array[String], signatures: Array[Array[String]]) : Array[IMethod] = {
		if(names == null || signatures == null){
			return new Array[IMethod](0)
		}
		var methods : HashSet[IMethod] = new HashSet[IMethod]()
		
		assert(names.length == signatures.length)
		for(i <- 0 until names.length){
			var method = typ.getMethod(names(i), signatures(i))
			methods.add(method)
		}
		return methods.toArray[IMethod]
	}
	
	private def getPossibleTargetClass(processor: PullUpRefactoringProcessor): Array[IType] = {
		return processor.getCandidateTypes(new RefactoringStatus(), new NullProgressMonitor)
	}
	
	private def setTargetClass(processor: PullUpRefactoringProcessor, targetClassIndex: Int): Unit = {
		var possibleClasses = getPossibleTargetClass(processor)
		var index = possibleClasses.length - 1 - targetClassIndex
		assert(index >= 0 && index < possibleClasses.length)
		processor.setDestinationType(possibleClasses(index))
	}
	
}

