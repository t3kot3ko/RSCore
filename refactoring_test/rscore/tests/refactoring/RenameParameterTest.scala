package rscore.tests.refactoring
import org.junit.Test
import rscore.dsl.entity.RSWorkspace
import org.eclipse.jdt.internal.corext.refactoring.structure.ChangeSignatureProcessor
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import scala.collection.JavaConverters._
import rscore.helper.RefactoringHelper

class RenameParameterTest extends RefactoringBaseTest {
	override val testGroupIdentifier = "rename_parameter"

	@Test
	def メソッドのパラメータの名前を変更できる(): Unit = {
		val testName = "SimpleTest"
		prepareTest(testName)

		val m = RSWorkspace.project(this.projectName).pkg(this.testGroupIdentifier).classes().first.firstMethod()
		val p = m.parameters().first
		p.rename("j")

		doAssert(testName)
	}

	@Test
	def パラメータを複数持つメソッドのパラメータ名を変更できる(): Unit = {
		val testName = "SimpleTest2"
		prepareTest(testName)

		val m = RSWorkspace.project(this.projectName).pkg(this.testGroupIdentifier).classes().first.firstMethod()
		m.parameters().foreach(e => e.rename("new" + e.name))

		doAssert(testName)
	}

	// At least, following description is required to realize rename-param refactoring
	@Test
	def sandbox(): Unit = {
		val testName = "SimpleTest"
		prepareTest(testName)

		val m = RSWorkspace.project(this.projectName).pkg(this.testGroupIdentifier).classes().first.firstMethod()
		val ps = m.parameters()
		val processor = new ChangeSignatureProcessor(ps.first.parent.origin)
		val ref = new ProcessorBasedRefactoring(processor)

		val infos = processor.getParameterInfos().asScala

		val newNames = ps.map(_ => "j")
		infos.zip(newNames).foreach(e => e._1.setNewName(e._2))

		RefactoringHelper.performRefactoring(ref)

		doAssert(testName)
	}

}