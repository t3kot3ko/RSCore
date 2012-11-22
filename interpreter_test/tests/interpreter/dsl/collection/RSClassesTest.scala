package tests.interpreter.dsl.collection
import tests.dsl.DSLBaseTest
import org.junit.Test
import org.junit.Assert._
import interpreter.RSInterpreter
import dsl.entity.RSClass
import interpreter.ScriptHelper
import tests.interpreter.InterpreterDSLBaseTest
import org.junit.Before
import scala.collection.mutable.ArraySeq

class RSClassesTest extends InterpreterDSLBaseTest{
	@Before
	override def setUp(): Unit = {
		super.setUp()
		prepareTest("RSClassesTest")
		val initScript = """project = RSWorkspace.project("%s")""".format(this.projectName) + "\n"
		interpreter.execScript(ScriptHelper.buildScript(initScript))
	}
	
	@Test
	def RSClassをオプションに応じて正しく取得できる(){
		prepareTest("RSClassesTest")
		
		val script = """
		classesWithInnerClass = project.pkg("find_test").classes(true)
		classesWithoutInnerClass = project.pkg("find_test").classes(false)
		"""
		interpreter.execScript(ScriptHelper.buildScript(script))
		val cwi = interpreter.getVariable[Array[RSClass]]("classesWithInnerClass") 
		val cwoi = interpreter.getVariable[Array[RSClass]]("classesWithoutInnerClass") 
		assertEquals(3, cwi.length)
		assertEquals(2, cwoi.length)
	}
	
	@Test
	def 名前からクラスを絞り込める(): Unit = {
		val script = """
			query = By.Name(With.or("RSClassesTest"))
			puts query
			puts project.pkg("find_test").classes(true).class
			"""
		interpreter.execScript(script)
		// assertEquals(1, c.length)
	}

}