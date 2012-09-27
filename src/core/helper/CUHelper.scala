package core.helper
import org.eclipse.jdt.core.IPackageFragment
import org.eclipse.jdt.core.ICompilationUnit

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
	
	def createCU(pack: IPackageFragment, name: String): ICompilationUnit = {
		if(pack.getCompilationUnit(name).exists()){
			return pack.getCompilationUnit(name)
		}
		return null
		
	}
}