package rscore.tests.general
import org.junit.Test
import rscore.util.StringHelper
import org.junit.Assert._

class StringHelperTest {
	@Test
	def replaceAllChainTest(): Unit = {
		val str = """$.PROJECT.First"""
		val pairs = Array[(String, String)](
				("""\$""", "RSWorkspace"),
				("PROJECT", "projects"),
				("First", "first"))
				
		val actual = StringHelper.replaceAllChain(str, pairs)
		val expected = "RSWorkspace.projects.first"
			
		assertEquals(expected, actual)
			
	}

}