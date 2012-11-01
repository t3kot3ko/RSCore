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
import org.eclipse.core.runtime.NullProgressMonitor
import java.io.FileNotFoundException
import org.junit.Assert
import core.util.FileUtil

class RefactoringBaseTest {
	var fgRoot: IPackageFragmentRoot = _
	var fgPackageP: IPackageFragment = _
	var fgJavaTestProject: IJavaProject = _
	var projectName: String = _

	// to be overridden in subclasses
	val testGroupIdentifier: String = ""

	@Before
	def setUp(): Unit = {
		println("RefactoringBaseTest :: setUp()")
		this.projectName = "TestProject" + System.currentTimeMillis()
		this.fgJavaTestProject = RefactoringTestHelper.createJavaProject(projectName, "bin")
		this.fgRoot = RefactoringTestHelper.addSourceContainer(this.fgJavaTestProject, "src")
		// this.fgPackageP = fgRoot.createPackageFragment("p", true, null)
		this.fgPackageP = fgRoot.createPackageFragment(testGroupIdentifier, true, null)
		this.fgJavaTestProject.open(new NullProgressMonitor)

		RefactoringCore.getUndoManager().flush()
	}

	protected def prepareTest(testName: String): Unit = {
		try {
			val inputFilepath = "test_resources_input/" + testGroupIdentifier + "/" + testName + ".java"
			val inputSource = FileUtil.getFileContents(inputFilepath)
			val cu = this.fgPackageP.createCompilationUnit(testName + ".java", inputSource, true, new NullProgressMonitor)
			cu.save(new NullProgressMonitor, true)
		} catch {
			case e: FileNotFoundException => Assert.fail()
		}

	}

	protected def doAssert(testName: String): Unit = {
		val actualSource = eliminateBlankLines(
			this.fgPackageP.getCompilationUnit(testName + ".java").getSource())

		val outputFilepath = "test_resources_output/" + testGroupIdentifier + "/" + testName + ".java"
		val expectedSource = eliminateBlankLines(
			FileUtil.getFileContents(outputFilepath))

		RefactoringTestHelper.assertEqualLines(expectedSource, actualSource)
	}

	private def eliminateBlankLines(string: String): String = {
		val blankLine = """^\s*$"""
		val lines = string.split("\n").filterNot(line => line.matches(blankLine) || line == "")
		return lines.mkString("\n")
	}

	@After
	def tearDown(): Unit = {
		println("RefactoringBaseTest :: tearDown()")
		RefactoringTestHelper.delete(this.fgJavaTestProject.getProject())
	}

}