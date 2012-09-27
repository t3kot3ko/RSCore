package core.helper
import org.eclipse.jdt.core.IPackageFragment
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.jdt.core.IPackageFragmentRoot

/**
 * CompilationUnit に関するヘルパメソッド群
 */

object CUHelper {
	private val TEST_PATH_PREFIX = ""
	private val TEST_INPUT_INFIX: String = "/in/"
	private val TEST_OUTPUT_INFIX: String = "/out/"
	private val CONTAINER: String = "src"

    /**
     * @param cuName
     * @param infix
     *            example "RenameTest/test0 + infix + cuName.java"
     */
	def getTestPath(): String = {
			return TEST_PATH_PREFIX
	}
	def createFileName(filepath: String): String = {
		return filepath + ".java"
	}
	
	// CompilationUnit を生成する．これをラップして，他に色々なヘルパを作るのがよいかも？
	private def createCU(pack: IPackageFragment, name: String, contents: String): ICompilationUnit = {
		if(pack.getCompilationUnit(name).exists()){
			return pack.getCompilationUnit(name)
		}
		else{
			// createCompilationUnit(String name, String contents, boolean force, IProgressMonitor monitor) 
			var cu: ICompilationUnit = pack.createCompilationUnit(name, contents, true, null)
			cu.save(null, true)
			return cu
		}
	}
	
	def getSourceFolder(project: IJavaProject, name: String): IPackageFragmentRoot = {
		var roots: Array[IPackageFragmentRoot] = project.getPackageFragmentRoots()
		return roots.find(e => !e.isArchive() && e.getElementName().equals(name)).get
	}
}