package rscore.tests.dsl.collection
import rscore.tests.dsl.DSLBaseTest
import org.junit.Before
import org.junit.Assert._
import rscore.dsl.entity.RSWorkspace
import rscore.dsl.entity.collection.By
import org.junit.Test
import rscore.dsl.entity.RSEntity

class RSMembersTest extends DSLBaseTest {
	override val testGroupIdentifier = "find_test"

	@Test
	def ƒNƒ‰ƒX‚©‚çƒƒ“ƒo‚ğ³‚µ‚­æ“¾‚Å‚«‚é(): Unit = {
		val testName = "RSMembersTest"
		prepareTest(testName)

		val cls = RSWorkspace.project(this.projectName).pkg(testGroupIdentifier).classes().select(By.Name(testName)).first()
		val members = cls.members()

		assertEquals(8, members.length)

		val methods = members.toArray.filter(_.kind == RSEntity.METHOD)
		assertEquals(3, methods.length)

		val fields = members.toArray.filter(_.kind == RSEntity.FIELD)
		assertEquals(5, fields.length)

		val staticMembers = members.select(By.Modifier("static"))
		assertEquals(2, staticMembers.length)

	}

}