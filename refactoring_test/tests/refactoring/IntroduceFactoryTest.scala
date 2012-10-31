package tests.refactoring
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.jdt.core.IPackageFragment
import core.util.FileUtil
import org.junit.Before
import org.junit.Assert
import org.junit.Test
import org.eclipse.core.runtime.NullProgressMonitor

class IntroduceFactoryTest extends RefactoringBaseTest {
	// @Before
	override def setUp(): Unit = {
		super.setUp()
		createCUForSimpleTest(this.fgPackageP)
	}
	
	// @Test
	def sandbox(): Unit = {
		Assert.assertTrue(true)
	}

	def createCUForSimpleTest(pack: IPackageFragment): Unit = {
		val filePath = "test_resources/IntroduceFactory/in1.java"
		val contents: String = "aaaa"
		val cu = pack.createCompilationUnit("AAA.java", contents, true, null)
		cu.save(new NullProgressMonitor, true)
	}

}