package rscore.tests.action.basic
import rscore.tests.refactoring.RefactoringBaseTest
import org.junit.Test
import org.junit.Assert._
import rscore.dsl.entity.RSProject
import org.junit.Before
import rscore.dsl.entity.RSWorkspace
import rscore.dsl.entity.collection.By
import org.eclipse.ltk.core.refactoring.participants.MoveProcessor
import org.eclipse.jdt.internal.corext.refactoring.structure.MoveStaticMembersProcessor
import org.eclipse.jdt.internal.ui.preferences.JavaPreferencesSettings
import org.eclipse.jdt.core.IMember
import org.eclipse.ltk.core.refactoring.participants.MoveRefactoring
import rscore.helper.RefactoringHelper
import org.eclipse.ltk.core.refactoring.CheckConditionsOperation
import org.eclipse.ltk.core.refactoring.CreateChangeOperation
import org.eclipse.ltk.core.refactoring.PerformChangeOperation
import org.eclipse.ltk.core.refactoring.RefactoringCore
import org.eclipse.ltk.core.refactoring.RefactoringStatus
import org.eclipse.ltk.core.refactoring.IUndoManager
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.NullProgressMonitor
import org.junit.After
import rscore.util.FileUtil
import org.junit.Ignore

class MoveMemberTest extends RefactoringBaseTest {
	override val testGroupIdentifier = "move_member"
	var project: RSProject = _

	@Before
	override def setUp(): Unit = {
		super.setUp()
		this.project = RSWorkspace.project(this.projectName)
	}

	private def generateDestClassName(base: String) = base + "Dest"
	private def prepareDestClass(destClassName: String): Unit = {
		val inputFilepath = "test_resources_input/" + "move_member/" + destClassName + ".java"
		val inputSource = FileUtil.getFileContents(inputFilepath)
		addCompilationUnit(destClassName, inputSource, this.fgPackageP)
	}

	@Test
	def クラス内のフィールドを別ファイル内のクラスに移動できる(): Unit = {
		val testName = "MoveFieldExternal"
		val destClassName = generateDestClassName(testName)

		prepareTest(testName)
		prepareDestClass(destClassName)
		
		val srcClass = project.pkg(testGroupIdentifier).classes().select(By.Name(testName)).first
		val destClass = project.pkg(testGroupIdentifier).classes().select(By.Name(destClassName)).first
		
		val fs = srcClass.fields()
		fs.moveMember(destClass)
		
		doAssert(testName)
		doAssert(destClassName)
	}


	@Test
	def クラス内のStaticメソッドを別ファイル内のクラスに移動できる(): Unit = {
		val testName = "MoveStaticMethodExternal"
		val destClassName = generateDestClassName(testName)
		
		prepareTest(testName)
		prepareDestClass(destClassName)
		
		val srcClass = project.pkg(testGroupIdentifier).classes().select(By.Name(testName)).first
		val destClass = project.pkg(testGroupIdentifier).classes().select(By.Name(destClassName)).first
		
		val ms = srcClass.methods().select(By.Modifier("static"))
		assert(ms.length == 1)
		
		ms.moveMember(destClass)
		
		doAssert(testName)
		doAssert(destClassName)

	}
	
	@Test
	def クラス内のStaticメソッドだけを別ファイル内のクラスに移動して参照元も変更できる(): Unit = {
		val testName = "MoveStaticMethodsExternal"
		val destClassName = generateDestClassName(testName)
		
		prepareTest(testName)
		prepareDestClass(destClassName)
		
		val srcClass = project.pkg(testGroupIdentifier).classes().select(By.Name(testName)).first
		val destClass = project.pkg(testGroupIdentifier).classes().select(By.Name(destClassName)).first
		
		val ms = srcClass.methods().select(By.Modifier("static"))
		assert(ms.length == 2)
		
		ms.moveMember(destClass)
		
		doAssert(testName)
		doAssert(destClassName)
	}
	
	
	// 以下が最低限の設定内容
	@Ignore
	def sandbox(): Unit = {
		val testName = "MoveFieldExternal"
		val destClassName = generateDestClassName(testName)

		prepareTest(testName)
		prepareDestClass(destClassName)

		assertEquals(2, project.pkg(testGroupIdentifier).classes().length)

		val srcClass = project.pkg(testGroupIdentifier).classes().select(By.Name(testName)).first
		val destClass = project.pkg(testGroupIdentifier).classes().select(By.Name(destClassName)).first

		val fs = srcClass.fields().toArray.map(_.origin.asInstanceOf[IMember])
		assert(fs.length == 3)

		val processor = new MoveStaticMembersProcessor(
			fs,
			JavaPreferencesSettings.getCodeGenerationSettings(project.origin()))

		println(destClass.origin().getFullyQualifiedName())
		processor.setDestinationTypeFullyQualifiedName(destClass.origin().getFullyQualifiedName())

		val ref = new MoveRefactoring(processor)
		RefactoringHelper.performRefactoring(ref)

		doAssert(testName)
		doAssert(destClassName)
	}

}