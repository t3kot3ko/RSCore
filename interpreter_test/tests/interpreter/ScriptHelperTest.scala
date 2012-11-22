package tests.interpreter
import org.junit.Test
import org.junit.Assert._
import interpreter.RSInterpreter
import util.FileUtil
import interpreter.ScriptHelper

class ScriptHelperTest{
	@Test
	def パッケージ宣言を正しく出力できる(): Unit = {
		val actual = ScriptHelper.buildImportDeclaration()
		val expected = """
					java_import 'dsl.entity.RSWorkspace'
					java_import 'dsl.entity.collection.By'
					java_import 'dsl.entity.collection.With'
					java_import 'dsl.entity.collection.WithOr'
					java_import 'dsl.entity.collection.WithAnd'
					"""
			assertLineBasedEquals(expected, actual)
	}
	
	/**
	 * 
	 */
	private def assertLineBasedEquals(expected: String, actual: String): Unit = {
		val e = FileUtil.eliminateBlankLines(expected, true)
		val a = FileUtil.eliminateBlankLines(actual, true)
		assertEquals(e, a)
	}
}