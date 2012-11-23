package tests.dsl.collection
import org.junit.Before
import org.junit.Test
import org.junit.Assert._
import dsl.util.ImplicitConversions._
import dsl.entity.collection.By
import dsl.entity.collection.With._
import dsl.entity.collection.WithOr
import dsl.entity.collection.With
import tests.dsl.DSLBaseTest
import dsl.entity.RSProject
import dsl.entity.RSWorkspace

class RSClassesTest extends DSLBaseTest{
	private var project: RSProject = _
	
	@Before
	override def setUp(): Unit = {
		super.setUp()
		prepareTest("RSClassesTest")
		project = $.project(this.projectName)
	}
	
	@Test
	def RSClassをオプションに応じて正しく取得できる(){
		var classesWithInnerClass = project.pkg("find_test").classes(true)
		assertEquals(3, classesWithInnerClass.length)
		
		var classesWithoutInnerClass = project.pkg("find_test").classes(false)
		assertEquals(2, classesWithoutInnerClass.length)
		
		// classes のオプションを指定しなければ，インナークラスも含めて返す
		assertEquals(3, project.pkg("find_test").classes.length)
	}
	
	@Test
	def 名前からクラスを絞り込める(): Unit = {
		var c = project.pkg("find_test").classes(true).select(By.Name(With.or("RSClassesTest")))
		assertEquals(1, c.length)
	}
	
	@Test
	def 修飾子からクラスを絞り込める(): Unit = {
		val c = project.pkg("find_test").classes(true).select(By.Modifier(With.or("public"))).select(By.Modifier(With.or("public")))
		assertEquals(1, c.length)
		val pc = project.pkg("find_test").classes(true).select(By.Modifier(With.or("public"))).select(By.Modifier(With.or("private")))
		assertEquals(0, pc.length)
	}
		
}
