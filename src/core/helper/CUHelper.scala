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
	
	private def formatUnitName(unitName: String): String = {
		val extension: String = ".java"
		if(unitName.indexOf(extension) == -1){
			return unitName + extension
		}
		return unitName
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