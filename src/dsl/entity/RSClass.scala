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
import dsl.entity.RSMethod._
import dsl.entity.RSMethod
import dsl.entity.collection.RSMethods
import dsl.search_trait.NameBasedSearchable
import dsl.search_trait.ModifierBasedSearchable


object RSClass{
	implicit def toRSClass(typ: IType) = new RSClass(typ: IType)
}
class RSClass(typ: IType) extends NameBasedSearchable with ModifierBasedSearchable{
	val name: String = this.typ.getElementName()
	
	def methods(): Array[RSMethod] = {
		return typ.getMethods().map(e => new RSMethod(e))
	}
	
	// Get method whose signature is matched
	def method(name: String, signature: Array[String]): RSMethod = {
		return typ.getMethod(name, signature)
	}
	
	
	// Get instance / class fields
	def fields() : Array[RSField] = {
		return this.typ.getFields().map(e => new RSField(e))
	}
	
	
	def ast() : TypeDeclaration = {
		var cu = this.typ.getCompilationUnit()
		var newCU = ASTUtil.createAST(cu).asInstanceOf[CompilationUnit]
		var types = newCU.types().asScala.map(e => e.asInstanceOf[TypeDeclaration])
		var target = types.find(e => e.getName().toString() == this.name).get
		assert(target != null)
		
		return target
	}
	
	override def getDeclaration(): TypeDeclaration = {
		var cu = ASTUtil.createAST(typ.getCompilationUnit()).asInstanceOf[CompilationUnit]
		var dec = ASTNodeSearchUtil.getTypeDeclarationNode(typ, cu)
		return dec
	}

}