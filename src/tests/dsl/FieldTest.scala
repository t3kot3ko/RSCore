package tests.dsl
import org.eclipse.ui.IWorkbench
import org.junit.Test
import org.junit.Assert._
import dsl.entity.RSWorkspace
import org.junit.Before
import dsl.entity.RSWorkspace
import dsl.entity.RSProject
import scala.reflect.This


class FieldTest extends BaseTest{
	@Before
	def setUp(): Unit = {
		this.project = $.project("Sample")
	}
	
	@Test
	def Workspace‚ðŽæ“¾‚Å‚«‚é(): Unit = {
		assertNotNull(project)
	}
	
	@Test
	def RSPackage‚ðŽæ“¾‚Å‚«‚é(): Unit = {
		var pkg = project.pkg("rename")
		assertNotNull(pkg)
	}
	
	@Test
	def RSClass‚ðŽæ“¾‚Å‚«‚é(): Unit = {
		var clss = project.pkg("rename").classes(false)
		assertNotNull(clss)
		assertEquals(2, clss.length)
	}
	
}