package tests.dsl.collection
import tests.dsl.BaseTest
import org.junit.Test
import org.junit.Before
import dsl.entity.RSMethod
import dsl.util.ImplicitConversions._
import dsl.entity.collection.By
import dsl.entity.collection.With
import org.junit.Ignore

class RSMethodsTest extends BaseTest{
	val methods: Array[RSMethod] = null
	
	/*
	@Ignore
	override def setUp(): Unit = {
		println("methods were collected")
		this.project.pkg("dsl.test").classes.first
		super.setUp()
	}
	
	@Ignore
	def 名前からメソッドを絞り込める(): Unit = {
		var targetMethodName = ""
		methods.select(By name With(targetMethodName))	
		
	}
	
	@Test
	def 返却値型からメソッドを絞り込める(): Unit = {
		
	}
	
	@Test
	def シグネチャからメソッドを絞り込める(): Unit = {
		
	}
	
	@Test
	def 修飾子からメソッドを絞り込める(): Unit = {
		
	}
	*/

}