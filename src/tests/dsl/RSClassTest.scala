package tests.dsl
import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class RSClassTest extends BaseTest{
	@Test
	def RSClassを正しく取得できる(){
		var classesWithInnerClass = project.pkg("test.dsl").classes(true)
		assertEquals(3, classesWithInnerClass.length)
		
		var classesWithoutInnerClass = project.pkg("test.dsl").classes(false)
		assertEquals(2, classesWithoutInnerClass.length)
		
		// classes のオプションを指定しなければ，インナークラスも含めて返す
		assertEquals(3, project.pkg("test.dsl").classes.length)
	}

}