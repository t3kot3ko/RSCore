package tests.dsl.collection
import org.junit.Before
import org.junit.Test
import org.junit.Assert._
import tests.dsl.BaseTest
import dsl.util.ImplicitConversions._
import dsl.entity.collection.By
import dsl.entity.collection.With

class RSClassesTest extends BaseTest{
	@Test
	def RSClassをオプションに応じて正しく取得できる(){
		var classesWithInnerClass = project.pkg("test.dsl").classes(true)
		assertEquals(3, classesWithInnerClass.length)
		
		var classesWithoutInnerClass = project.pkg("test.dsl").classes(false)
		assertEquals(2, classesWithoutInnerClass.length)
		
		// classes のオプションを指定しなければ，インナークラスも含めて返す
		assertEquals(3, project.pkg("test.dsl").classes.length)
	}
	
	@Test
	def 名前からクラスを絞り込める(): Unit = {
		var c = project.pkg("test.dsl").classes(true).select(By.name(With("FindTest1")))
		assertEquals(1, c.length)
	}
	
	@Test
	def 修飾子からクラスを絞り込める(): Unit = {
		var c = project.pkg("test.dsl").classes(true).select(By.modifier(With("public")))
		assertEquals(2, c.length)
	}
		
}