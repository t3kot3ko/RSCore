package rscore.tests.interpreter
import rscore.tests.common.BaseTest
import org.junit.Before
import rscore.interpreter.RSInterpreter
import rscore.tests.dsl.DSLBaseTest

class InterpreterDSLBaseTest extends DSLBaseTest{
	protected var interpreter = RSInterpreter
	
	@Before
	override def setUp(): Unit = {
		super.setUp()
		interpreter.initContainer()
	}

}