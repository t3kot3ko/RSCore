package tests.dsl.collection
import org.junit.Test
import org.junit.Before
import dsl.entity.RSMethod
import dsl.util.ImplicitConversions._
import dsl.entity.collection.By
import dsl.entity.collection.WithOr
import org.junit.Ignore
import org.junit.Assert._
import dsl.entity.collection.With
import scala.collection.mutable.ArraySeq
import tests.dsl.DSLBaseTest
import dsl.entity.collection.RSCollection

class RSMethodsTest extends DSLBaseTest{
	private var methods: RSCollection[RSMethod] = _
	
	@Before
	override def setUp(): Unit = {
		super.setUp()
		prepareTest("RSMethodsTest")
		this.methods = $.project(this.projectName).pkg("find_test").classes.first.methods
		println("methods were collected")
	}
	
	@Test
	def 名前からメソッドを絞り込める(): Unit = {
		val targetMethodName = "publicVoidMethod"
		val results = methods.select(By.Name(With.or(targetMethodName)))
		assertEquals(1, results.length)
		assertEquals(targetMethodName, results.first.name)
	}
	
	@Test
	def 正規表現からメソッドを絞り込める(): Unit = {
		assertEquals(1, methods.select(By.Namereg(With.or("publicStaticVoidMethod"))).length)
		
		val prMethods = methods.select(By.Namereg(With.or("^pr.*Method$")))
		assertEquals(2, prMethods.length)
	}
	
	@Test
	def 返却値型からメソッドを絞り込める(): Unit = {
		var voidMethods = methods.select(By.Type(With.or("void")))
		assertEquals(2, voidMethods.length)
	}
	
	@Test
	def シグネチャからメソッドを絞り込める(): Unit = {
		
		
	}
	
	@Test
	def 修飾子からメソッドを絞り込める(): Unit = {
		val publicMethods = methods.select(By.Modifier(With.or("public")))
		assertEquals(2, publicMethods.length)
		
		val publicStaticMethods = methods.select(By.Modifier(With.and(Array("public", "static"))))
		assertEquals(1, publicStaticMethods.length)
	}
	
	@Ignore
	def コールバックからメソッドを絞り込める(): Unit = {
		// パラメータが1つ以上のメソッドを探す
		val callback: RSMethod => Boolean = 
			(method: RSMethod) => method.parameters.length > 0
		// val methodsHaveParameter: ArraySeq[RSMethod] = methods.select(By.Callback(With.or(callback)))
		// assertEquals(2, methodsHaveParameter.length)
	}

}