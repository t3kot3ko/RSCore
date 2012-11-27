package rscore.tests.dsl.entity
import org.junit.Test
import org.junit.Before
import rscore.dsl.entity.RSMethod
import org.junit.Assert._
import rscore.tests.dsl.DSLBaseTest
import rscore.dsl.entity.collection.RSCollection

class MethodTest extends DSLBaseTest {
	var methods: RSCollection[RSMethod] = _

	@Before
	override def setUp(): Unit = {
		super.setUp()
		prepareTest("RSMethodsTest")
		this.methods = $.project(this.projectName).pkg("find_test").classes.first.methods
		assertEquals(4, this.methods.length)
	}
	
	@Test
	def イテレータに関するテスト(): Unit = {
		println("###############################")
		println(methods.foreach(m => println(m.name)))
		println("###############################")
	}
	
	@Test
	def メソッドの名前を正しく取得できる(): Unit = {
		var expectedNames = Array[String](
			"publicVoidMethod",
			"privateIntMethod",
			"protectedStringMethod",
			"publicStaticVoidMethod")
		for(i <- 0 until expectedNames.length){
			assertEquals(expectedNames(i), methods(i).name)
		}
	}

	@Test
	def メソッドの戻り値を正しく取得できる(): Unit = {
		
	}

	@Test
	def メソッドのシグネチャを正しく取得できる(): Unit = {

	}

}