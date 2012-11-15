package tests.dsl.collection
import org.eclipse.ui.IWorkbench
import org.junit.Test
import org.junit.Assert._
import dsl.entity.RSWorkspace
import org.junit.Before
import dsl.entity.RSWorkspace
import dsl.entity.RSProject
import scala.reflect.This
import dsl.entity.collection.By
import dsl.entity.collection.With
import dsl.util.ImplicitConversions._
import tests.dsl.BaseTest
import dsl.entity.RSClass

class RSFieldsTest extends BaseTest{
	var cls: RSClass = null
	
	@Before
	override def setUp(): Unit ={
		super.setUp()
		this.cls = project.pkg("test.dsl").classes.first
	}
	
	@Test
	def 名前からフィールドを絞り込める(): Unit = { 
		var publicIntField = cls.fields.selects(By.Name(With.or("publicInt", "aaa")))
		assertEquals(1, publicIntField.length)
	}
	
	@Test
	def 修飾子からフィールドを絞り込める(): Unit = {
		var publicFields = this.cls.fields.selects(By.Modifier(With.or("public")))
		assertEquals(1, publicFields.length)
		
		var privateFields = this.cls.fields.selects(By.Modifier(With.or("private" )))
		assertEquals(1, privateFields.length)
		
		var protectedFields = this.cls.fields.selects(By.Modifier(With.or("protected")))
		assertEquals(1, protectedFields.length)
		
		var publicStaticFields = this.cls.fields.selects(By Modifier With.and("public", "static"))
		assertEquals(0, publicStaticFields.length)
	}
	
	@Test
	def 型名からフィールドを絞り込める(): Unit = {
		var intFields = this.cls.fields.selects(By Type(With.or("int")))
		assertEquals(2, intFields.length)
	}
	
	@Test
	def 正規表現でフィールドを絞り込める(): Unit = {
		var prFields = this.cls.fields.selects(By.Namereg(With.or("^pr")))
		assertEquals(2, prFields.length)
	}
}