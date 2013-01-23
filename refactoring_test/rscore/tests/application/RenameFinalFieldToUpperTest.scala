package rscore.tests.application
import org.junit.Test
import org.junit.Assert._
import rscore.dsl.entity.collection.By
import scala.util.matching.Regex
import rscore.dsl.entity.RSField

class RenameFinalFieldToUpperTest extends ApplicationTest {
	@Test
	def Finalなフィールドのうち大文字になっていないものを大文字にする(): Unit = {
		val testName = "RenameFinalFieldToUpperTest"
		prepareTest(testName)

		def charMatch(s: String, r: Regex): Boolean = {
			return r.findFirstIn(s).isDefined
		}
		def isValidName(s: String): Boolean = charMatch(s, """^[A-Z]*$""".r)
		def generateNewName(str: String): String = {
			var f: Boolean = true
			var result = ""

			str.foreach(c => {
				if (c.isLower) {
					result += c.toUpper
					f = true
				} else if (c.isUpper) {
					if (f) {
						result += (if (result == "") c else "_" + c)
						f = false
					} else {
						result += c
					}
				}
			})

			return result
		}

		def renameFinal(f: RSField): Unit = {
			val newName = generateNewName(f.name)
			println(newName)
			f.rename(newName)
		}
		val fs = getFirstClass().fields().select(By modifier "final")
		assertEquals(3, fs.count)
		
		fs.foreach(renameFinal(_))

		doAssert(testName)

	}

}