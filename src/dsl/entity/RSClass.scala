package dsl.entity
import org.eclipse.jdt.core.IType
import org.eclipse.jdt.core.dom.ASTNode
import dsl.util.ASTUtil
import org.eclipse.jdt.core.dom.CompilationUnit
import scala.collection.JavaConverters._
import org.eclipse.jdt.core.dom.TypeDeclaration
import dsl.entity.RSField
import org.eclipse.jdt.core.dom.FieldDeclaration
import org.eclipse.jdt.core.dom.VariableDeclaration
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
import org.eclipse.jdt.core.IMethod
import org.eclipse.jdt.core.dom.Modifier

class RSClass(var name: String, typ: IType){
	def methods(): Array[RSMethod] = {
		return this.typ.getMethods().map(e => RSMethod.create(e))
	}
	
	def hasVisibility(method: IMethod, visibility: Int): Boolean = {
		var cu = this.typ.getCompilationUnit()
		var newCu = ASTUtil.createAST(cu).asInstanceOf[CompilationUnit]
		var dec = ASTNodeSearchUtil.getMethodDeclarationNode(method, newCu)
		
	}
	
	def fields() : Array[RSField] = {
		return this.typ.getFields().map(e => RSField.create(e))
	}
	
	
	def ast() : TypeDeclaration = {
		var cu = this.typ.getCompilationUnit()
		var newCU = ASTUtil.createAST(cu).asInstanceOf[CompilationUnit]
		var types = newCU.types().asScala.map(e => e.asInstanceOf[TypeDeclaration])
		var target = types.find(e => e.getName().toString() == this.name).get
		assert(target != null)
		
		return target
	}

}