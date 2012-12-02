package rscore.tests.interpreter
import org.junit.Test
import org.junit.Assert._
import rscore.interpreter.RSInterpreter
import org.junit.After
import org.junit.Before
import org.jruby.embed.LocalVariableBehavior

class BasicTest{
	var interpreter = RSInterpreter
	
	@Before
	def setUp(): Unit = {
		interpreter.initContainer(LocalVariableBehavior.PERSISTENT, false)
	}
	@After
	def tearDown(): Unit = {
		interpreter.terminateContainer()
	}
	
	@Test
	def 整数演算を正しく解釈できる(): Unit = {
		val script = """1+1"""
		val r = interpreter.execScript[Long](script)
		assertEquals(2, r) 
	}
	
	@Test
	def 文字列演算を正しく解釈できる(): Unit = {
		val script = """"hello " + "world""""
		val r = interpreter.execScript[String](script)
		assertEquals("hello world", r) 
	}
	
	@Test
	def 変数のセットと取り出しができる(): Unit = {
		val variableName = "variable"
		val expected = 123
		interpreter.assignVariable(variableName, expected)
		
		// NOTE: arithmetic number cannnot be casted to Int
		val actual = interpreter.getVariable[Long](variableName).get
		
		assertEquals(expected, actual)
	}
	
	@Test
	def 不正なスクリプトは解釈中に例外を投げる(): Unit = {
		val script = """pputs "foo" """
		try{
			interpreter.execScript(script)
			fail()
		} catch{
			case _  => assertTrue(true)
		}
	}
}