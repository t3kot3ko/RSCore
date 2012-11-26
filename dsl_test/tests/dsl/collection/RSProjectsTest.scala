package tests.dsl.collection
import tests.dsl.DSLBaseTest
import org.junit.Test
import dsl.entity.collection.By
import org.junit.Assert._

class RSProjectsTest extends DSLBaseTest{
	@Test
	def sandbox() = {
		prepareTest()
		assertTrue("This project should be found", $.projects.select(By.Name(this.projectName)).found)
		assertEquals("This project should be found", 1, $.projects.select(By.Name(this.projectName)).length)
		assertTrue($.projects.length >= 1)
	}

}