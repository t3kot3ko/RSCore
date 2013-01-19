package rscore.tests.interpreter.action.refactoring
import rscore.tests.interpreter.InterpreterRefactoringBaseTest
import org.junit.Before
import org.junit.Test

class RenameMethodTest extends InterpreterRefactoringBaseTest{
	override val testGroupIdentifier = "rename_method"
		
	@Test
	def privateな1メソッドをリネームできる(): Unit = {
		val testName = "RenamePrivateMethods"
		prepareTest(testName)

		val script = """
		cls = RSWorkspace.project("%s").pkg("%s").classes.first
		cls.methods.first.rename("mm1")
		""".format(this.projectName, this.testGroupIdentifier)
		
		interpreter.execScript(script)

		doAssert(testName)
	}

}