package rscore.tests.common
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.core.resources.IWorkspaceRoot
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.resources.IFolder
import org.eclipse.core.runtime.IPath
import org.eclipse.jdt.internal.ui.util.CoreUtility
import org.eclipse.jdt.core.JavaCore
import org.eclipse.jdt.core.IClasspathEntry
import org.eclipse.core.resources.IProjectDescription
import org.eclipse.core.resources.IWorkspaceRunnable
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.jdt.core.IPackageFragmentRoot
import org.eclipse.core.resources.IContainer
import org.eclipse.jdt.internal.corext.util.Strings
import org.junit.Assert
import org.eclipse.jdt.core.IPackageFragment
import org.eclipse.jdt.core.ICompilationUnit
import java.io.File
import org.eclipse.core.runtime.Path

object TestHelper {
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

		var outputLocation: IPath = null
		if (binFolderName != null && binFolderName.length > 0) {
			var binFolder: IFolder = project.getFolder(binFolderName)
			if (!binFolder.exists()) {
				CoreUtility.createFolder(binFolder, false, true, null)
			}
			outputLocation = binFolder.getFullPath()
		} else {
			outputLocation = project.getFullPath()
		}

		if (!project.hasNature(JavaCore.NATURE_ID)) {
			val description: IProjectDescription = project.getDescription()
			val prevNatures = description.getNatureIds()

			val natureId: String = JavaCore.NATURE_ID
			val newNatures = (prevNatures.toBuffer + natureId).toArray
			description.setNatureIds(newNatures)
			project.setDescription(description, null)
		}

		// println(outputLocation)
		var javaProject: IJavaProject = JavaCore.create(project)
		javaProject.setOutputLocation(outputLocation, null)
		javaProject.setRawClasspath(new Array[IClasspathEntry](0), null)

		return javaProject
	}

	/**
	 * 指定された IJavaElement 要素を削除する
	 */
	def delete(elem: IResource): Unit = {
		val runnable = new IWorkspaceRunnable() {
			override def run(monitor: IProgressMonitor): Unit = {
				elem.delete(true, new NullProgressMonitor)
			}
		}
		ResourcesPlugin.getWorkspace().run(runnable, null)
	}

	/**
	 * Adds a source container to a IJavaProject.
	 */
	def addSourceContainer(
		jproject: IJavaProject,
		containerName: String = "src",
		inclusionFilters: Array[IPath] = new Array[IPath](0),
		exclusionFilters: Array[IPath] = new Array[IPath](0), 
		outputLocation: String = null): IPackageFragmentRoot = {
		
		val p: IProject = jproject.getProject()
		var container: IContainer = null
		if(containerName == null || containerName.length() == 0){
			container = p
		}
		else{
			val folder: IFolder = p.getFolder(containerName)
			if(!folder.exists()){
				CoreUtility.createFolder(folder, false, true, null)
			}
			container = folder
		}
		val root: IPackageFragmentRoot = jproject.getPackageFragmentRoot(container)
		
		var outputPath: IPath = null
		if(outputLocation != null){
			val folder = p.getFolder(outputLocation)
			if(!folder.exists()){
				CoreUtility.createFolder(folder, false, true, null)
			}
			outputPath = folder.getFullPath()
		}
		val cpe = JavaCore.newSourceEntry(root.getPath(), inclusionFilters, exclusionFilters, outputPath)
		addToClasspath(jproject, cpe)
		
		return root
	}
	
	private def addToClasspath(jproject: IJavaProject, cpe: IClasspathEntry): Unit = {
		val oldEntries = jproject.getRawClasspath()
		if(oldEntries.exists(_.equals(cpe))){
			return;
		}
		val newEntries = (oldEntries.toBuffer + cpe).toArray
		jproject.setRawClasspath(newEntries, new NullProgressMonitor)
	}
	
	/**
	 * ライブラリを（プロジェクトに対して）追加する
	 */
	def addLibrary(project: IJavaProject, path: IPath, sourceAttachPath: IPath, sourceAttachRoot: IPath): IPackageFragmentRoot = {
		val cpe: IClasspathEntry = JavaCore.newLibraryEntry(path, sourceAttachPath, sourceAttachRoot)
		addToClasspath(project, cpe)
		val workspaceResource = ResourcesPlugin.getWorkspace().getRoot().findMember(path)
		if(workspaceResource != null){
			return project.getPackageFragmentRoot(workspaceResource)
		}
		return project.getPackageFragmentRoot(path.toString())
	}
	
	
	/**
	 * 必要な基本ライブラリを追加する
	 * （これがないと，組込型以外のハンドリングができない）
	 */
	def addRTJar(project: IJavaProject/*, path: IPath = RT_JAR_PATH*/): IPackageFragmentRoot = {
		val RT_JAR_PATH = new Path("/System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Classes/classes.jar")
		val rtJarPath = Array[IPath](
				RT_JAR_PATH, 
				null,
				null
		)
		
		setCompilerOption(project)
		return addLibrary(project, rtJarPath(0), rtJarPath(1), rtJarPath(2))
	}
	
	def setCompilerOption(project: IJavaProject): Unit = {
		val options = project.getOptions(false)
		project.setOptions(options)
	}
	
	
	/**
	 * Line-based version of assertEquals(String, String)
	 * without considering line delimiters
	 */
	
	def assertEqualLines(expected: String, actual: String): Unit = {
		assertEqualLines("", expected, actual)
	}
	def assertEqualLines(message: String = "", expected: String, actual: String): Unit = {
		val expectedLines = Strings.convertIntoLines(expected)
		val actualLines = Strings.convertIntoLines(actual)
		
		val expected2 = Strings.concatenate(expectedLines, "\n")
		val actual2 = Strings.concatenate(actualLines, "\n")
		Assert.assertEquals(message, expected2, actual2)
	}
	
	def createCU(pack: IPackageFragment, name: String, contents: String): ICompilationUnit = {
		Assert.assertFalse(pack.getCompilationUnit(name).exists())
		val cu = pack.createCompilationUnit(name, contents, true, null)
		cu.save(new NullProgressMonitor, true)
		return cu
	}
	
	
}