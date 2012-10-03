package core.helper
import org.eclipse.jdt.core.IPackageFragment
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.jdt.core.IPackageFragmentRoot
import core.MyPlugin
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.resources.IWorkspace
import org.eclipse.core.resources.IWorkspaceRoot
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.resources.IFolder
import org.eclipse.jdt.internal.ui.util.CoreUtility
import org.eclipse.core.runtime.IPath
import org.eclipse.jdt.core.JavaCore
import org.eclipse.jdt.core.IClasspathEntry
import org.eclipse.jdt.core.IType

/**
 * CompilationUnit に関するヘルパメソッド群
 * TODO: Rename
 */

object CUHelper {
	private val TEST_PATH_PREFIX = ""
	private val TEST_INPUT_INFIX: String = "/in/"
	private val TEST_OUTPUT_INFIX: String = "/out/"
	private val CONTAINER: String = "src"

	/**
	 * プロジェクト名，パッケージ名，CU名，クラス名から，それを満たす IType を取得する
	 * className を省略したら，cuName と同じクラス名のクラスを探しに行く
	 */
	def findTargetType(projectName: String, packageName: String, cuName: String, className: String): IType = {
		var project = getJavaProject(projectName)
		var root = getSourceFolder(project)
		var pack = root.getPackageFragment(packageName)
		var cu = getCompilationUnit(pack, cuName)
		var typ = cu.getType(className)
		
		return typ
	}
	def findTargetType(projectName: String, packageName: String, cuName: String): IType = {
		return findTargetType(projectName, packageName, cuName, cuName)
	}
	
		
	/**
	 * パッケージ以下から，CompilationUnit を取得する
	 * @param unitName		取得する CompilationUnit の名前（＝ファイル名）
	 */
	def getCompilationUnit(pack: IPackageFragment, unitName: String): ICompilationUnit = {
		var name = formatUnitName(unitName)
		if (pack.getCompilationUnit(name).exists()) {
			return pack.getCompilationUnit(name)
		}
		return null
	}
	
	/**
	 * CompilationUnit 名は，ファイル名でもいいし拡張子でも良い．
	 */
	private def formatUnitName(unitName: String): String = {
		val extension: String = ".java"
		if(unitName.indexOf(extension) == -1){
			return unitName + extension
		}
		return unitName
	}


	/**
	 * ソースフォルダ（IPackageFragmentRoot）を取得する
	 * @param project			プロジェクト（getJavaProject() で取得する）
	 * @param srcFolderName		Source folder's name (default: "src")
	 */
	def getSourceFolder(project: IJavaProject, srcFolderName: String = "src"): IPackageFragmentRoot = {
		var roots: Array[IPackageFragmentRoot] = project.getPackageFragmentRoots()
		return roots.find(e => !e.isArchive() && e.getElementName().equals(srcFolderName)).get
	}

	/**
	 * ランタイムワークベンチのワークスペース以下から，JavaProject を取得する．見つからなかったら null を返す
	 *
	 * @param projectName Project's name
	 * @return project: IJavaProject (if found, otherwise null)
	 */
	def getJavaProject(projectName: String): IJavaProject = {
		var root: IWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot()
		var project: IProject = root.getProject(projectName)

		if (!project.exists()) {
			return null
		}

		project.refreshLocal(IResource.DEPTH_INFINITE, null)
		if (!project.isOpen()) {
			project.open(null)
		}

		var javaProject: IJavaProject = JavaCore.create(project)
		return javaProject
	}

	@deprecated
	private def createCU(pack: IPackageFragment, name: String, contents: String): ICompilationUnit = {
		if (pack.getCompilationUnit(name).exists()) {
			return pack.getCompilationUnit(name)
		} else {
			// createCompilationUnit(String name, String contents, boolean force, IProgressMonitor monitor) 
			var cu: ICompilationUnit = pack.createCompilationUnit(name, contents, true, null)
			cu.save(null, true)
			return cu
		}
	}
	
	@deprecated
	def createJavaProject(projectName: String, binFolderName: String = "bin"): IJavaProject = {
		var root: IWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot()
		var project: IProject = root.getProject(projectName)

		if (!project.exists()) {
			project.create(null)
		} else {
			project.refreshLocal(IResource.DEPTH_INFINITE, null)
		}
		if (!project.isOpen()) {
			project.open(null)
		}

		var binFolder: IFolder = project.getFolder(binFolderName)

		var outputLocation: IPath = null
		if (!binFolder.exists()) {
			CoreUtility.createFolder(binFolder, false, true, null)
			outputLocation = binFolder.getFullPath()
		} else {
			outputLocation = project.getFullPath()
		}

		println(outputLocation)

		var descripttion = project.getDescription()
		var javaProject: IJavaProject = JavaCore.create(project)

		return javaProject
	}
}