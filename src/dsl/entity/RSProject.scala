package dsl.entity
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.jdt.core.IPackageFragmentRoot

class RSProject(project: IJavaProject) {
	def packages: Array[RSPackage] = {
		var root = project.getPackageFragmentRoot("src")
		return null
	}

	def packages(srcDirName: String): Array[RSPackage] = {
		var root = getRoot()
		return null
	}
	
	def pkg(packageName: String): RSPackage = {
		var root = getRoot()
		return new RSPackage(root.getPackageFragment(packageName))
		
	}

	private def getRoot(srcDirName: String = "src"): IPackageFragmentRoot = {
		var roots: Array[IPackageFragmentRoot] = project.getPackageFragmentRoots()
		var root = roots.find(e => !e.isArchive() && e.getElementName().equals(srcDirName)).get
		return root
	}

}