package tests.refactoring
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.jdt.core.IPackageFragment
import core.util.FileUtil
import org.junit.Before
import org.junit.Assert
import org.junit.Test
import org.eclipse.core.runtime.NullProgressMonitor
import org.junit.After
import dsl.entity.RSWorkspace
import dsl.util.ImplicitConversions._
import dsl.entity.collection.By
import dsl.entity.collection.With


class IntroduceFactoryTest extends RefactoringBaseTest {
	@Before
	override def setUp(): Unit = {
		super.setUp()
	}
	
	@Test
	def test0(): Unit = {
		val inputFilename = "test_resources/IntroduceFactory/" + "0/In.java"
		val inputSource = FileUtil.getFileContents(inputFilename)
		println(inputFilename)
		println(inputSource)
		val cu = this.fgPackageP.createCompilationUnit("in1.java", inputSource, true, new NullProgressMonitor)
		cu.save(new NullProgressMonitor, true)
		
		
		val $ = RSWorkspace
		val projName = this.fgJavaTestProject.getElementName()
		$.project(projName).pkg("p").classes.select(By.Name(With.or("Foo"))).first.introduce_factory
		
		Assert.assertTrue(true)
	}
	
	@After
	override def tearDown(): Unit = {
		
	}
	
	


}