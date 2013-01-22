package rscore.tests.application
import org.junit.Test
import rscore.dsl.entity.collection.By
import rscore.dsl.entity.collection.With

class EncapsulateAllPublicFieldsTest extends ApplicationTest{
	@Test
	def publicフィールドのうちstaticでないものをすべてカプセル化する(): Unit = {
		val testName = "EncapsulateAllPublicFieldsTest"
		prepareTest(testName)

		val c = getCurrentProject().pkg(this.testGroupIdentifier).classes().Select_one(By name testName)
		val pfs = c.fields.select(By modifier "public").select(By modifier (With out "static"))
		pfs.foreach(_.encapsulate())

		doAssert(testName)
	}
}