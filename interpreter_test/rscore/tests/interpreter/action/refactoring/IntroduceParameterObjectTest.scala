package rscore.tests.interpreter.action.refactoring
import rscore.tests.interpreter.InterpreterRefactoringBaseTest
import rscore.util.FileUtil
import org.junit.Test
import org.junit.Assert._
import rscore.dsl.entity.collection.By

class IntroduceParameterObjectTest extends InterpreterRefactoringBaseTest {
	override val testGroupIdentifier = "introduce_parameter_object"

	/**
	 * 新たにファイル（CompilationUnit）が生成されるリファクタリングなので
	 * doAssert を別に用意する
	 */
	def doAssert_(subGroupIdentifier: Int, ignoreComment: Boolean = true): Unit = {
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

		val script1 = """
		method = project.pkg("%s").classes.first.methods.first
		method.introduce_parameter_object()
		""".format(this.testGroupIdentifier)
		
		interpreter.execScript(script1)

		doAssert_(0)

		val paramName = "MParameter"
		val cus = $.project(this.projectName).pkg(testGroupIdentifier).classes

		val paramObjCu = cus.select(By.Name(paramName)).first.origin().getCompilationUnit()
		val actualSource = FileUtil.eliminateBlankLines(paramObjCu.getSource())

		val path = generateOutputFilePath("introduce_parameter_object/0/" + paramName)
		val expectedSource = FileUtil.getFileContents(path, true)

		doAssertHelper(expectedSource, actualSource, true)
	}

}