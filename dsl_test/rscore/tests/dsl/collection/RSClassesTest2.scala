package rscore.tests.dsl.collection
import rscore.tests.dsl.DSLBaseTest
import org.junit.Before
import rscore.dsl.entity.RSProject
import org.junit.Test
import org.junit.Assert._
import rscore.dsl.entity.collection.By

class RSClassesTest2 extends DSLBaseTest {
	override val testGroupIdentifier = "find_test_classes"
	private var project: RSProject = _

	@Before
	override def setUp(): Unit = {
		super.setUp()
		prepareTest("RSClassesTest2")
		project = $.project(this.projectName)
	}

	@Test
	def インナークラスの数を正しく取得できる(): Unit = {
		val clss = project.pkg("find_test_classes").classes(false)
		assertEquals(1, clss.length)

		assertEquals("RSClassesTest2", clss.first.name)

		val cls = clss.first
		assertTrue(cls.hasInnerclass())

		val innerClasses = cls.innerclasses()
		assertEquals(3, innerClasses.length)
	}

	@Test
	def ネストしたインナークラスを正しく取得できる(): Unit = {
		val nestedInner = project.pkg("find_test_classes").classes(false).first.innerclasses().Select(By.Name("NestedInner")).first
		val cls1 = nestedInner.innerclasses.Select(By.Name("Inner1")).first
		val cls2 = nestedInner.innerclasses.Select(By.Name("Inner2")).first

		assertFalse(cls1.hasInnerclass())
		assertEquals(2, cls2.innerclasses().length)
	}
	

}