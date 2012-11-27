package rscore.sandbox
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.jdt.core.IMember
import org.eclipse.jdt.internal.corext.refactoring.RefactoringAvailabilityTester
import org.eclipse.jdt.internal.corext.refactoring.structure.PullUpRefactoringProcessor
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import org.eclipse.ltk.core.refactoring.Refactoring
import org.eclipse.jdt.internal.ui.preferences.JavaPreferencesSettings
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.ltk.core.refactoring.Change
import org.eclipse.jdt.core.IType
import org.eclipse.jdt.core.IField
import scala.collection.mutable.HashSet
import org.eclipse.jdt.core.IMethod
import org.eclipse.ltk.core.refactoring.RefactoringStatus
import org.eclipse.jdt.internal.corext.refactoring.structure.UseSuperTypeProcessor
import org.eclipse.ltk.core.refactoring.CreateChangeOperation
import org.eclipse.ltk.core.refactoring.CheckConditionsOperation
import org.eclipse.ltk.core.refactoring.PerformChangeOperation
import org.eclipse.core.resources.ResourcesPlugin

object PullUp {
	def pullUpSample(unit: ICompilationUnit): Unit = {
		var typ = unit.getType("Bar")
		var members: Array[IMember] = typ.getFields().map(e => e.asInstanceOf[IMember])
		println(members.length)

		println("########################")
		println(RefactoringAvailabilityTester.isPullUpAvailable(members))

		var project = members(0).getJavaProject()
		assert(project != null)
		var processor = new PullUpRefactoringProcessor(members, JavaPreferencesSettings.getCodeGenerationSettings(project))
		new ProcessorBasedRefactoring(processor)
		var refactoring: Refactoring = processor.getRefactoring()
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
		var undo: Change = change.perform(pm)

		// undo.perform(pm)

	}

	// TODO: ProcessorBasedRefactoring ‚Éì‚è•Ï‚¦‚Ä‚Ý‚éiˆÈ‰º‚Í“®‚©‚È‚¢I
	def pullUpSample2(unit: ICompilationUnit): Unit = {
		var typ = unit.getType("Bar")
		var members: Array[IMember] = typ.getFields().map(e => e.asInstanceOf[IMember])
		
		var project = members(0).getJavaProject()
		assert(project != null)
		var processor = new PullUpRefactoringProcessor(members, JavaPreferencesSettings.getCodeGenerationSettings(project))
		
		var refactoring = new ProcessorBasedRefactoring(processor)
		var check: CheckConditionsOperation = new CheckConditionsOperation(refactoring, CheckConditionsOperation.ALL_CONDITIONS)
		var create = new CreateChangeOperation(check, RefactoringStatus.FATAL)
		var perform = new PerformChangeOperation(create)
		
		var pm = new NullProgressMonitor()
		ResourcesPlugin.getWorkspace().run(perform, pm)
		
		var status: RefactoringStatus = create.getConditionCheckingStatus()
		if(status.isOK()){
			println("ok")
		}
		else{
			println(status)
		}


	}

	// Helpers
	private def getFields(typ: IType, names: Array[String]): Array[IField] = {
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
	private def getMethods(typ: IType, names: Array[String], signatures: Array[Array[String]]): Array[IMethod] = {
		if (names == null || signatures == null) {
			return new Array[IMethod](0)
		}
		var methods: HashSet[IMethod] = new HashSet[IMethod]()

		assert(names.length == signatures.length)
		for (i <- 0 until names.length) {
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