package tests.interpreter
import tests.common.BaseTest
import org.junit.Before
import interpreter.RSInterpreter
import tests.dsl.DSLBaseTest

class InterpreterDSLBaseTest extends DSLBaseTest{
	protected var interpreter = RSInterpreter
	
	@Before
	override def setUp(): Unit = {
		super.setUp()
		interpreter.initContainer()
	}

}