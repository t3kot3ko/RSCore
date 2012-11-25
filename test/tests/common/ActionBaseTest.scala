package tests.common
import util.FileUtil

/**
 * ソースコード変形が伴う場合のテスト
 * Known subclasses: RefactoringBaseTest
 */
class ActionBaseTest extends BaseTest{
	def generateOutputFilePath(location: String): String = {
		val outputFilePath = "test_resources_output/" + location + ".java"
		return outputFilePath
	}
	
	def doAssertHelper(expectedSource: String, actualSource: String, ignoreComment: Boolean = false): Unit = {
		if (ignoreComment) {
			TestHelper.assertEqualLines(
				FileUtil.eliminateCommentLines(expectedSource),
				FileUtil.eliminateCommentLines(actualSource))

		} else {
			TestHelper.assertEqualLines(expectedSource, actualSource)
		}
	}

}