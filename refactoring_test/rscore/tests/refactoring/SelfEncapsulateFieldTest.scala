package rscore.tests.refactoring
import rscore.dsl.entity.RSWorkspace
import org.junit.Test
import org.junit.Before
import rscore.dsl.entity.RSProject
import rscore.dsl.entity.collection.By
import rscore.dsl.entity.collection.With

class SelfEncapsulateFieldTest extends RefactoringBaseTest{
	override val testGroupIdentifier = "sef"
		
	@Before
	override def setUp(): Unit = {
		super.setUp()
	}
		
	@Test
	def simpleTest(): Unit = {
		val testName = "SimpleTest"
		prepareTest(testName)
		RSWorkspace.project(this.projectName).pkg(this.testGroupIdentifier).classes().first.fields().first.encapsulate()
		doAssert(testName = testName, ignoreComment = true)
	}
	
	@Test
	def compositeTest(): Unit = {
		val testName = "CompositeTest"
		prepareTest(testName)
		RSWorkspace.project(this.projectName).pkg(this.testGroupIdentifier).classes().first.fields().select(By.modifier(With.out("protected"))).foreach(_.encapsulate)
		doAssert(testName = testName, ignoreComment = true)
	}
	

}