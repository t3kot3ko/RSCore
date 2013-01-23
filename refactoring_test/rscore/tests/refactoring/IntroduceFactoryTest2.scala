package rscore.tests.refactoring
import org.junit.Test
import rscore.dsl.entity.collection.By
import org.junit.After

class IntroduceFactoryTest2 extends RefactoringBaseTest{
	override val testGroupIdentifier = "introduce_factory_2"
		
	@Test
	def スーパークラスに(): Unit = {
		val testName = "SetToOtherClass"
		val testName2 = "SuperClass"
		val testName3 = "Client"
			
		prepareTest(testName)
		prepareTest(testName2)
		prepareTest(testName3)

		val c = $.project(projectName).pkg(testGroupIdentifier).classes.select(By.Name(testName)).first
		c.introduce_factory(c.superclass())

		doAssert(testName)
		doAssert(testName2)
		doAssert(testName3)
	}

}