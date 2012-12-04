package rscore.tests.refactoring
import rscore.dsl.entity.RSWorkspace
import org.junit.Test

class IntroduceIndirectionTest extends RefactoringBaseTest {
	override val testGroupIdentifier = "introduce_indirection"
		
	@Test
	def test01(): Unit = {
		val testName = "test01"
		prepareTest(testName)
		
		val m = RSWorkspace.project(this.projectName).pkg(testGroupIdentifier).classes().first.methods.first
		assert(m.name == "m")
		
		m.introduceIndirection()
		doAssert(testName)
	}

}