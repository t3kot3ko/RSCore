package rscore.tests.refactoring
import org.junit.Test
import rscore.util.FileUtil
import rscore.tests.common.TestHelper
import rscore.tests.common.BaseTest
import rscore.dsl.entity.RSWorkspace
import org.junit.Assert._
import org.junit.After
import rscore.dsl.entity.collection.By
import org.eclipse.ltk.core.refactoring.participants.DeleteProcessor
import org.eclipse.jdt.internal.corext.refactoring.reorg.JavaDeleteProcessor
import rscore.tests.common.ActionBaseTest

class IntroduceParameterObjectTest extends ActionBaseTest {
	override val testGroupIdentifier = "introduce_parameter_object"

	/**
	 * 新たにファイル（CompilationUnit）が生成されるリファクタリングなので
	 * doAssert を別に用意する
	 */
	def doAssert(subGroupIdentifier: Int, ignoreComment: Boolean = true): Unit = {
		val testName = generateTestName(subGroupIdentifier)

		val actualSource = FileUtil.eliminateBlankLines(
			this.fgPackageP.getCompilationUnit(testName + ".java").getSource())

		val outputFilepath = "test_resources_output/" + testGroupIdentifier + "/" + subGroupIdentifier + "/" + testName + ".java"
		val expectedSource = FileUtil.getFileContents(outputFilepath)

		doAssertHelper(expectedSource, actualSource, ignoreComment)
	}

	private def generateTestName(subId: Int): String = {
		val PREFIX = "IntroduceParameterObjectTest"
		val testName = PREFIX + subId.toString()
		return testName;
	}

	private def prepare(subId: Int): Unit = {
		val testName = generateTestName(subId)
		prepareTest(testName)
	}

	@Test
	def パラメータオブジェクトが外部クラスとして生成される(): Unit = {
		prepare(0)
		val method = $.project(this.projectName).pkg(testGroupIdentifier).classes.first.methods.first
		assertEquals("m", method.name)

		method.introduceParameterObject()
		doAssert(0)

		val paramName = "MParameter"
		val cus = $.project(this.projectName).pkg(testGroupIdentifier).classes

		assertEquals(
			"The number of compilation units should be increased",
			2,
			cus.length)

		assertTrue(
			"The new compilation unit should be created",
			cus.select(By.Name(paramName)).found)

		val paramObjCu = cus.select(By.Name(paramName)).first.origin().getCompilationUnit()
		val actualSource = FileUtil.eliminateBlankLines(paramObjCu.getSource())

		val path = generateOutputFilePath("introduce_parameter_object/0/" + paramName)
		val expectedSource = FileUtil.getFileContents(path, true)

		doAssertHelper(expectedSource, actualSource, true)

	}
}