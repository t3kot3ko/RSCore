package rscore.tests.refactoring
import org.junit.Test
import rscore.dsl.entity.RSWorkspace
import rscore.dsl.entity.collection.With
import rscore.dsl.entity.collection.By
import org.junit.Assert._

class InlineTest extends RefactoringBaseTest{
	override val testGroupIdentifier = "inline"
		

	@Test
	def sandbox(): Unit = {
		val testName = "InlineTest0"
		prepareTest(testName)
		val projectName = this.projectName
		
		val $ = RSWorkspace
		val finalIntFields = $.project(projectName).pkg(testGroupIdentifier).classes.first.fields()
		.select(By.Modifier(With.and("final", "static")))
		// .select(By.Modifier("public"))
		
		assertEquals(1, finalIntFields.length)
		
		finalIntFields.first.inline()
		doAssert(testName, true)
	}

}