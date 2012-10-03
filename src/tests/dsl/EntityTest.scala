package tests.dsl
import org.junit.Test
import org.junit.Assert._
import org.junit.Before
import org.eclipse.jdt.core.ICompilationUnit
import core.helper.CUHelper
import org.junit.runners.Parameterized.Parameters
import java.util.Arrays
import dsl.entity.RSClass

class EntityTest {
	var cu: ICompilationUnit

	private val PACKAGE_NAME = "testcases"
	private val PROJECT_NAME = "EntityTest"
	private val UNIT_NAME = ""


	@Before
	def setUp(unitName: String): Unit = {
		var project = CUHelper.getJavaProject(PROJECT_NAME)
		var root = CUHelper.getSourceFolder(project)
		var pack = root.getPackageFragment(PACKAGE_NAME)
		var unit = CUHelper.getCompilationUnit(pack, UNIT_NAME)
		
		this.cu = unit
	}
	
	@Test
	def ƒƒ\ƒbƒh‚Ì”‚ğ³‚µ‚­æ“¾‚Å‚«‚é: Unit = {
		var typ = this.cu.getType(UNIT_NAME)
		var $ = new RSClass(UNIT_NAME, typ)
		assertEquals(3, $.methods.length)
	}
	
	
}