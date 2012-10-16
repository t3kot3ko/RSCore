package dsl.entity
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.resources.IWorkspaceRoot
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.jdt.core.JavaCore
import dsl.entity.RSWorkspace

object RSWorkspace{
	val root: IWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot()

	def project(projectName: String): RSProject = {
		var project: IProject = root.getProject(projectName)

		if (!project.exists()) {
			return null
		}

		project.refreshLocal(IResource.DEPTH_INFINITE, null)
		if (!project.isOpen()) {
			project.open(null)
		}

		var javaProject: IJavaProject = JavaCore.create(project)
		return new RSProject(javaProject)
	}
}