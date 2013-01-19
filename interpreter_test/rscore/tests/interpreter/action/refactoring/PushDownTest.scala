package rscore.tests.interpreter.action.refactoring
import rscore.tests.interpreter.InterpreterRefactoringBaseTest
import org.junit.Test

class PushDownTest extends InterpreterRefactoringBaseTest {
	override val testGroupIdentifier = "push_down"

	@Test
	def クラスメンバすべてを一気に同一ファイル内のサブクラスにPushDownできる(): Unit = {
		val testName = "InnerSimple"
		prepareTest(testName)

		val script = """
		cls = project.pkg("%s").classes.Select(By.Name "%s").first
		members = cls.members
		members.push_down
			""".format(this.testGroupIdentifier, testName)
			
		interpreter.execScript(script)
		
		doAssert(testName, true)
	}

}