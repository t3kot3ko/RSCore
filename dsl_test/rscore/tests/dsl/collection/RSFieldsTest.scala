package rscore.tests.dsl.collection
import org.eclipse.ui.IWorkbench
import org.junit.Test
import org.junit.Assert._
import rscore.dsl.entity.RSWorkspace
import org.junit.Before
import rscore.dsl.entity.RSWorkspace
import rscore.dsl.entity.RSProject
import scala.reflect.This
import rscore.dsl.entity.collection.By
import rscore.dsl.entity.collection.With
import rscore.dsl.util.ImplicitConversions._
import rscore.dsl.entity.RSClass
import rscore.tests.dsl.DSLBaseTest

class RSFieldsTest extends DSLBaseTest{
	private var cls: RSClass = _ 
	
	@Before
	override def setUp(): Unit ={
		super.setUp()
		prepareTest("RSFieldsTest")
		this.cls = $.project(this.projectName).pkg("find_test").classes.first
	}
	
	@Test
	def 名前からフィールドを絞り込める(): Unit = { 
		var publicIntField = cls.fields.select(By.Name(With.or("publicInt", "aaa")))
		assertEquals(1, publicIntField.length)
	}
	
	@Test
	def 修飾子からフィールドを絞り込める(): Unit = {
		var publicFields = this.cls.fields.select(By.Modifier(With.or("public")))
		assertEquals(1, publicFields.length)
		
		var privateFields = this.cls.fields.select(By.Modifier(With.or("private" )))
		assertEquals(1, privateFields.length)
		
		var protectedFields = this.cls.fields.select(By.Modifier(With.or("protected")))
		assertEquals(1, protectedFields.length)
		
		var publicStaticFields = this.cls.fields.select(By Modifier With.and(Array("public", "static")))
		assertEquals(0, publicStaticFields.length)
	}
	
	@Test
	def 型名からフィールドを絞り込める(): Unit = {
		var intFields = this.cls.fields.select(By Type(With.or("int")))
		assertEquals(2, intFields.length)
	}
	
	@Test
	def 正規表現でフィールドを絞り込める(): Unit = {
		var prFields = this.cls.fields.select(By.Namereg(With.or("^pr")))
		assertEquals(2, prFields.length)
	}
}