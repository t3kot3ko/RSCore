package application.sample
import core.helper.CUHelper
import org.eclipse.jdt.core.IField
import org.eclipse.jdt.core.dom.ASTParser
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.core.dom.FieldDeclaration
import org.eclipse.jdt.core.dom.Modifier
import scala.collection.mutable.Buffer
import org.eclipse.jdt.core.dom.AST
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.jdt.core.dom.VariableDeclarationFragment
import core.sandbox.RenameField
import scala.collection.JavaConverters._
import org.eclipse.jdt.core.dom.TypeDeclaration

object RenameMultipleFields {
	/**
	 * プライベートなフィールドのプレフィクスにアンダースコアをつける
	 */
	def renamePrivateFieldWithUnderscoreSample(): Unit = {
		var project = CUHelper.getJavaProject("Sample")
		assert(project != null)

		var root = CUHelper.getSourceFolder(project)
		assert(root != null)

		var pack = root.getPackageFragment("rename")
		assert(pack != null)

		var cu = CUHelper.getCompilationUnit(pack, "RenameField")
		assert(cu != null)

		var typ = cu.getTypes().first
		var fields: Array[IField] = typ.getFields()

		var parser = ASTParser.newParser(AST.JLS2)
		parser.setKind(ASTParser.K_COMPILATION_UNIT)
		parser.setSource(cu)
		var ast = parser.createAST(new NullProgressMonitor)

		var node = ast.asInstanceOf[CompilationUnit]
		var types = node.types().asScala.map(e => e.asInstanceOf[TypeDeclaration])
		for (t <- types) {
			println(t.getName().toString())
		}
		var target = types(0)
		var astFields: Array[FieldDeclaration] = target.getFields()
		var privateFields: Array[FieldDeclaration] = astFields.filter(e => Modifier.isPrivate(e.getModifiers()))
		
		def firstFragmentName(e: FieldDeclaration): String= {
			var fragments: Buffer[VariableDeclarationFragment] = e.fragments().asScala.map(e => e.asInstanceOf[VariableDeclarationFragment])
			return fragments.first.getName().toString()
		}
		var privateFieldNames: Array[String] = privateFields.map(e => firstFragmentName(e))
		for(n <- privateFieldNames){
			println(n)
		}
		
		for(name <- privateFieldNames){
			var newName = (if (name.startsWith("_")) "" else "_") + name
			println(newName)
			RenameField.renameFieldRefactoringSample2(cu, name, newName)
		}
		
	}

}