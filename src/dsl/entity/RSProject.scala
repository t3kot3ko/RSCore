package dsl.entity
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.jdt.core.IPackageFragmentRoot
import dsl.traits.search.NameBasedSearchable

class RSProject(element: IJavaProject)
	extends RSEntity
	with NameBasedSearchable {
	
	override val __identifier = "project"
	override def origin = element
	override val name = element.getElementName()

	def pkg(packageName: String): RSPackage = {
		var root = getRoot()
		return new RSPackage(root.getPackageFragment(packageName))

	}

	private def getRoot(srcDirName: String = "src"): IPackageFragmentRoot = {
		var roots: Array[IPackageFragmentRoot] = element.getPackageFragmentRoots()
		var root = roots.find(e => !e.isArchive() && e.getElementName().equals(srcDirName)).get
		return root
	}

}