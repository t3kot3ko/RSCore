package tests.refactoring
import org.junit.Before
import org.junit.After
import org.eclipse.jdt.core.IPackageFragmentRoot
import org.eclipse.jdt.core.IPackageFragment
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.core.resources.IWorkspaceRunnable
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.ltk.core.refactoring.RefactoringCore
import org.junit.Test
import org.junit.Assert._

class RefactoringBaseTest {
	var fgRoot: IPackageFragmentRoot = _
	var fgPackageP: IPackageFragment = _
	var fgJavaTestProject: IJavaProject = _
	// var fgJRELibrary: IPackageFragmentRoot = _

	@Before
	def setUp(): Unit = {
		println("RefactoringBaseTest :: setUp()")
		this.fgJavaTestProject = RefactoringTestHelper.createJavaProject("TestProject" + System.currentTimeMillis(), "bin")
		this.fgRoot = RefactoringTestHelper.addSourceContainer(this.fgJavaTestProject, "src")
		this.fgPackageP = fgRoot.createPackageFragment("p", true, null)
		
		RefactoringCore.getUndoManager().flush()
	}
	
	private def restoreTestProject(): Unit = {
		val javaProject: IJavaProject = fgRoot.getJavaProject()
		if(javaProject.exists()){
		}
	}
	
	@Test
	def sample(): Unit = {
		assertTrue(true)
	}

	@After
	def tearDown(): Unit = {
		// RefactoringTestHelper.delete(this.fgJavaTestProject.getProject())
	}

}