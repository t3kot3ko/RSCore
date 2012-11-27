package dsl.entity
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.resources.IWorkspaceRoot
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.jdt.core.JavaCore
import org.eclipse.core.runtime.NullProgressMonitor
import dsl.entity.collection.RSCollection

object RSWorkspace{
	val root: IWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot()

	def refresh(): Unit = {
		root.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor)
	}
	
	/**
	 * Get a project whose name is 'projectName'
	 */
	def project(projectName: String): RSProject = {
		var project: IProject = root.getProject(projectName)

		if (!project.exists()) {
			return null
		}

		project.refreshLocal(IResource.DEPTH_INFINITE, null)
		if (!project.isOpen()) {
			project.open(null)
		}

		var javaProject: IJavaProject = createJavaProject(project)
		return new RSProject(javaProject)
	}
	
	/**
	 * Get all projects among workspace
	 */
	def projects(): RSCollection[RSProject] = {
		val projects = root.getProjects().map(e => new RSProject(createJavaProject(e)))
		return new RSCollection[RSProject](projects)
	}
	
	private def createJavaProject(project: IProject): IJavaProject = {
		return JavaCore.create(project)
	}
}