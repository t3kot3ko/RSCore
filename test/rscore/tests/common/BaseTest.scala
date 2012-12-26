package rscore.tests.common
import rscore.dsl.entity.RSWorkspace
import rscore.dsl.entity.RSProject
import org.junit.Before
import org.junit.Test
import org.junit.Assert._
import org.eclipse.jdt.core.IPackageFragmentRoot
import org.eclipse.jdt.core.IPackageFragment
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.ltk.core.refactoring.RefactoringCore
import rscore.util.FileUtil
import java.io.FileNotFoundException
import junit.framework.Assert
import org.junit.After

/**
 * 基底テスト
 * （テスト用のプロジェクトを作って，テストが終わったらそれを削除する）
 */
class BaseTest {
	protected var fgRoot: IPackageFragmentRoot = _
	protected var fgPackageP: IPackageFragment = _
	protected var fgJavaTestProject: IJavaProject = _
	protected var fgJRELibrary: IPackageFragmentRoot = _
	
	// 自動的に決定されるテストプロジェクトの名前
	var projectName: String = _
	
	// 入力に与えるソースコードと同じ名前をつける
	var testName: String = _

	// to be overridden in subclasses
	val testGroupIdentifier: String = ""
		
	// alias
	val $ = RSWorkspace
	
	/**
	 * ソースディレクトリ直下にパッケージを追加して，その参照を返す
	 */
	protected def addPackage(name: String): IPackageFragment = {
		return this.fgRoot.createPackageFragment(name, true, null)
	}
	
	/**
	 * パッケージ pkg 以下に CompilationUnit を追加する
	 */
	protected def addCompilationUnit(name: String, source: String, pkg: IPackageFragment): Unit = {
		val cu = pkg.createCompilationUnit(name + ".java", source, true, new NullProgressMonitor)
		cu.save(new NullProgressMonitor, true)
	}

	@Before
	def setUp(): Unit = {
		println(testGroupIdentifier + " setUp()")
		this.projectName = "TestProject" + System.currentTimeMillis()
		this.fgJavaTestProject = TestHelper.createJavaProject(projectName, "bin")
		this.fgRoot = TestHelper.addSourceContainer(this.fgJavaTestProject, "src")
		// this.fgPackageP = fgRoot.createPackageFragment("p", true, null)
		this.fgJRELibrary = addRTJar(fgJavaTestProject)
		this.fgPackageP = fgRoot.createPackageFragment(testGroupIdentifier, true, null)
		this.fgJavaTestProject.open(new NullProgressMonitor)

		RefactoringCore.getUndoManager().flush()
	}

	protected def prepareTest(testName: String): Unit = {
		this.testName = testName
		try {
			val inputFilepath = "test_resources_input/" + testGroupIdentifier.replaceAll("""\.""", "/") + "/" + testName + ".java"
			val inputSource = FileUtil.getFileContents(inputFilepath)
			val cu = this.fgPackageP.createCompilationUnit(testName + ".java", inputSource, true, new NullProgressMonitor)
			cu.save(new NullProgressMonitor, true)
		} catch {
			case e: FileNotFoundException => {
				e.printStackTrace()
				Assert.fail("input file is not found" )	
			}
		}
	}
	
	// プロジェクトだけ作りたい時
	protected def prepareTest(): Unit = {}
	
	def addRTJar(project: IJavaProject): IPackageFragmentRoot = {
		return TestHelper.addRTJar(project)
	}

	@After
	def tearDown(): Unit = {
		println(testGroupIdentifier + " tearDown()")
		TestHelper.delete(this.fgJavaTestProject.getProject())
	}
	
}