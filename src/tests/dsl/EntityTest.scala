package tests.dsl
import org.junit.Test
import org.junit.Assert._
import org.junit.Before
import org.eclipse.jdt.core.ICompilationUnit
import core.helper.CUHelper
import org.junit.runners.Parameterized.Parameters
import java.util.Arrays
import dsl.entity.RSClass
import dsl.entity.collection.RSMethods._
import dsl.entity.collection.RSMethods
import dsl.entity.RSMethod

class EntityTest {
	var cu: ICompilationUnit = null

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
	def ÉÅÉ\ÉbÉhÇÃêîÇê≥ÇµÇ≠éÊìæÇ≈Ç´ÇÈ: Unit = {
		var typ = this.cu.getType(UNIT_NAME)
		var $ = new RSClass(typ)
		var privateMethods: RSMethods = $.methods.privateMethods
		assertEquals(3, $.methods.length)
	}
	
	
}