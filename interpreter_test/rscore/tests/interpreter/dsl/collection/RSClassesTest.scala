package rscore.tests.interpreter.dsl.collection
import rscore.tests.dsl.DSLBaseTest
import org.junit.Test
import org.junit.Assert._
import rscore.interpreter.RSInterpreter
import rscore.dsl.entity.RSClass
import rscore.interpreter.ScriptHelper
import rscore.tests.interpreter.InterpreterDSLBaseTest
import org.junit.Before
import scala.collection.mutable.ArraySeq
import rscore.dsl.entity.collection.RSCollection

class RSClassesTest extends InterpreterDSLBaseTest {
	@Before
	override def setUp(): Unit = {
		super.setUp()
		prepareTest("RSClassesTest")
		val initScript = """project = RSWorkspace.project("%s")""".format(this.projectName) + "\n"
		// interpreter.execScript(ScriptHelper.buildScript(initScript))
		interpreter.execScript(initScript)
	}

	@Test
	def RSClassをオプションに応じて正しく取得できる() {
		prepareTest("RSClassesTest")

		val script = """
		classesWithInnerClass = project.pkg("find_test").classes(true)
		classesWithoutInnerClass = project.pkg("find_test").classes(false)
		"""
		// interpreter.execScript(ScriptHelper.buildScript(script))
		interpreter.execScript(script)
		val cwi = interpreter.getVariable[RSCollection[RSClass]]("classesWithInnerClass").get
		val cwoi = interpreter.getVariable[RSCollection[RSClass]]("classesWithoutInnerClass").get
		assertEquals(3, cwi.length)
		assertEquals(2, cwoi.length)
	}

	@Test
	def 名前からクラスを絞り込める(): Unit = {
		val script = """
			query = By.Name(With.or("RSClassesTest"))
			c = project.pkg("find_test").classes(true).select(query)
			"""
		interpreter.execScript(script)

		val c = interpreter.getVariable[RSCollection[RSClass]]("c").get
		assertEquals(1, c.length)
	}

}