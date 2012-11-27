package tests.interpreter.dsl.collection
import tests.interpreter.InterpreterDSLBaseTest
import interpreter.ScriptHelper
import org.junit.Before
import dsl.entity.collection.RSCollection
import dsl.entity.RSMethod
import org.junit.Assert._
import org.junit.Test
import org.jruby.RubyArray
import org.junit.Ignore

class RSMethodsTest extends InterpreterDSLBaseTest {
	@Before
	override def setUp(): Unit = {
		super.setUp()
		prepareTest("RSMethodsTest")
		val initScript = """project = RSWorkspace.project("%s")""".format(this.projectName) + "\n"
		interpreter.execScript(ScriptHelper.buildScript(initScript))

		val methods = $.project(this.projectName).pkg("find_test").classes.first.methods
		interpreter.assignVariable("methods", methods)
	}

	@Test
	def 名前からメソッドを絞り込める() = {
		val targetMethodName = "publicVoidMethod"
		val script = """
			result = methods.Select(By.Name("%s"))
		""".format(targetMethodName)
		interpreter.execScript(script)

		val result = interpreter.getVariable[RSCollection[RSMethod]]("result")
		assertEquals(1, result.length)
		assertEquals(targetMethodName, result.first.name)
	}

	// Using ruby embedded functions such as Array(Enumerable)#select
	@Test
	def 名前からメソッドを絞り込める2(): Unit = {
		val targetMethodName = "publicVoidMethod"
		val script = """
			ms = project.pkg("find_test").classes.first.methods.rsElements
			result = ms.select{|m| m.name == "%s"}
		""".format(targetMethodName)
		interpreter.execScript(script)

		val resultCount = interpreter.getVariable[Long]("result.length")
		assertEquals(1, resultCount)

		assertEquals(targetMethodName, interpreter.getVariable[String]("result.first.name"))
	}

	@Test
	def 正規表現からメソッドを絞り込める(): Unit = {
		val script = """
			foundMethods = methods.Select(By.Namereg(With.or("publicStaticVoidMethod")))
			prMethods = methods.select(By.Namereg(With.or("^pr.*Method$")))
			"""
		interpreter.execScript(script)

		assertEquals(1, interpreter.getVariable[Long]("foundMethods.length"))
		assertEquals(2, interpreter.getVariable[Long]("prMethods.length"))
	}
	@Test
	def 返却値型からメソッドを絞り込める(): Unit = {
		val script = """
			voidMethods = methods.select(By.Type(With.or("void")))
			"""
		interpreter.execScript(script)
		assertEquals(2, interpreter.getVariable[Long]("voidMethods.length"))
	}
}