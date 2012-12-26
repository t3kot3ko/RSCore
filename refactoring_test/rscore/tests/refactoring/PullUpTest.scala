package rscore.tests.refactoring
import org.junit.Test
import org.junit.Assert._
import org.eclipse.jdt.internal.corext.refactoring.structure.PullUpRefactoringProcessor
import rscore.dsl.entity.RSWorkspace
import org.eclipse.jdt.internal.ui.preferences.JavaPreferencesSettings
import org.eclipse.jdt.core.IMember
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import rscore.helper.RefactoringHelper
import rscore.dsl.entity.collection.By
import rscore.dsl.entity.RSClass
import org.junit.Ignore

class PullUpTest extends RefactoringBaseTest {
	private def generateSubclassName(className: String) = className + "SubClass"
	override val testGroupIdentifier = "pullup"

	/**
	 * MEMO: これが PullUpRefactoring, ~Processor の最低限の設定
	 */
	@Ignore
	def Test0(): Unit = {
		val testName = "PullUpTest0"
		prepareTest(testName)

		val projectName = this.projectName
		val members: Array[IMember] = RSWorkspace.project(projectName).pkg(testGroupIdentifier).classes().Select(By.Name("SubClass"))
			.first.fields().origin.map(_.origin())
		assertEquals(1, members.length)

		val superClass = RSWorkspace.project(projectName).pkg(testGroupIdentifier).classes().first

		val project = members.first.getJavaProject()
		val processor = new PullUpRefactoringProcessor(members, JavaPreferencesSettings.getCodeGenerationSettings(project))

		processor.setDestinationType(superClass.origin())
		new ProcessorBasedRefactoring(processor)

		val ref = processor.getRefactoring()

		RefactoringHelper.performRefactoring(ref)

		doAssert(testName)
	}

	@Test
	def フィールド単体を同一ファイル内のスーパークラスに引き上げる(): Unit = {
		val testName = "PullUpTest0"
		prepareTest(testName)

		val projectName = this.projectName
		val subClass: RSClass = RSWorkspace.project(projectName).pkg(testGroupIdentifier).classes().Select(By.Name("SubClass")).first
		val field = subClass.fields().first

		val superClass = RSWorkspace.project(projectName).pkg(testGroupIdentifier).classes().first
		field.pullUp(superClass)

		doAssert(testName)
	}
	
	// Using superclass() query
	@Test
	def フィールド単体を同一ファイル内のスーパークラスに引き上げる2(): Unit = {
		val testName = "PullUpTest0"
		prepareTest(testName)

		val projectName = this.projectName
		val subClass: RSClass = RSWorkspace.project(projectName).pkg(testGroupIdentifier).classes().Select(By.Name("SubClass")).first
		val field = subClass.fields().first

		assertTrue(subClass.hasSuperclass())
		val superClass = subClass.superclass()
		field.pullUp(superClass)

		doAssert(testName)
	}

	@Test
	def 複数のフィールドを同一ファイル内のスーパークラスに引き上げる(): Unit = {
		val testName = "PullUpMultiFieldsTest"
		prepareTest(testName)
		
		val project = RSWorkspace.project(this.projectName)
		val pkg = project.pkg(this.testGroupIdentifier)

		val subClass: RSClass = project.pkg(testGroupIdentifier).classes().Select(By.Name(generateSubclassName(testName))).first
		val fields = subClass.fields()

		val superClass = project.pkg(testGroupIdentifier).classes().first
		fields.pullUp(superClass)
		doAssert(testName)
	}

	@Test
	def メソッド単体を同一ファイル内のスーパークラスに引き上げる(): Unit = {
		val testName = "PullUpSingleMethodTest"
		prepareTest(testName)

		val project = RSWorkspace.project(this.projectName)
		val pkg = project.pkg(this.testGroupIdentifier)
		
		val subClass = pkg.classes().Select(By.Name(generateSubclassName(testName))).first
		val superClass = pkg.classes.first

		val method = subClass.firstMethod()
		method.pullUp(superClass)
		doAssert(testName)
	}

	@Test
	def 複数メソッドを同一ファイル内のスーパークラスに引き上げる(): Unit = {
		val testName = "PullUpMultiMethodsTest"
		prepareTest(testName)

		val project = RSWorkspace.project(this.projectName)
		val pkg = project.pkg(this.testGroupIdentifier)
		
		val subClass = pkg.classes().Select(By.Name(generateSubclassName(testName))).first
		val superClass = pkg.classes.first

		val methods = subClass.methods
		methods.pullUp(superClass)
		doAssert(testName)
	}
}