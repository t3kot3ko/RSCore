package rscore.tests.application
import org.junit.Test
import rscore.util.FileUtil
import rscore.dsl.entity.collection.By

class IntroduceParameterToComplicatedParametersTest extends ApplicationTest {
	// TODO: extract below helper methods
	def doAssert2(testName: String, ignoreComment: Boolean = true): Unit = {
		val actualSource = FileUtil.eliminateBlankLines(
			this.fgPackageP.getCompilationUnit(testName + ".java").getSource())

		val outputFilepath = "test_resources_output/" + testGroupIdentifier + "/" + "IntroduceParameterObject" + "/" + testName + ".java"
		val expectedSource = FileUtil.getFileContents(outputFilepath)

		doAssertHelper(expectedSource, actualSource, ignoreComment)
	}

	@Test
	def 特定数以上のパラメータを持つ関数にパラメータオブジェクトを導入する(): Unit = {
		val testName = "IntroduceParameterToComplicatedParametersTest"
		prepareTest(testName)

		val threshold = 3
		val methods = getFirstClass().methods().filter(_.parameters().count >= threshold)

		methods.foreach(_.introduceParameterObject())

		doAssert2(testName) // test for original

		// tests for generated parameterobject classes
		(3 to 5).foreach(i => {
			val pObjName = "M%sParameter".format(i)
			val actualSource = FileUtil.eliminateBlankLines(getCurrentProject().pkg("application").classes.selectOne(By name pObjName).origin().getCompilationUnit().getSource())
			val expectedSource = FileUtil.getFileContents("test_resources_output/application/IntroduceParameterObject/" + pObjName + ".java", true)
			doAssertHelper(expectedSource, actualSource, true)
		})
	}

}