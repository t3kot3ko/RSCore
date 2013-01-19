package rscore.tests.interpreter
import org.junit.Before
import rscore.tests.refactoring.RefactoringBaseTest
import rscore.interpreter.RSInterpreter

class InterpreterRefactoringBaseTest extends RefactoringBaseTest{
	protected var interpreter = RSInterpreter
	
	@Before
	override def setUp(): Unit = {
		super.setUp()
		interpreter.initContainer()
		// val initScript = """project = RSWorkspace.project("%s")""".format(this.projectName) + "\n"
		// interpreter.execScript(initScript)
		val project = $.project(this.projectName)
		interpreter.assignVariable("project", project)
	}

}