package rscore.tests.refactoring
import rscore.dsl.entity.RSWorkspace
import org.junit.Test
import org.junit.Assert._
import rscore.dsl.entity.collection.By

class RenameMethodTest extends RefactoringBaseTest {
	override val testGroupIdentifier = "rename_method"

	@Test
	def privateな1メソッドをリネームできる(): Unit = {
		val testName = "RenamePrivateMethods"
		prepareTest(testName)

		val cls = RSWorkspace.project(this.projectName).pkg(testGroupIdentifier).classes.first
		val m = cls.methods.first

		assertEquals(m.name, "m1")
		m.rename("mm1")

		doAssert(testName)
	}

	@Test
	def publicな1メソッドをリネームできる(): Unit = {
		val testName = "RenamePublicMethods"
		prepareTest(testName)
		
		val cls = RSWorkspace.project(this.projectName).pkg(testGroupIdentifier).classes.first
		val m = cls.methods.Select(By.Modifier("public")).first

		assertEquals(m.name, "m1")
		m.rename("mm1")

		doAssert(testName)

	}
}