package tests.refactoring
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

object RefactoringTestHelper {
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

		println(outputLocation)
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
		exclusionFilters: Array[IPath] = new Array[IPath](0)): IPackageFragmentRoot = {
		
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
		return root
	}
}