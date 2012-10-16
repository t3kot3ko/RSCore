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
import dsl.common.RSParams
import dsl.entity.collection.RSMethods._
import dsl.search_trait.CallbackBasedSearchable
import dsl.common.RSParam


object RSClass{
	implicit def toRSClass(typ: IType) = new RSClass(typ: IType)
}
class RSClass(typ: IType) extends RSEntity(typ) with NameBasedSearchable with ModifierBasedSearchable with CallbackBasedSearchable[RSClass]{
	val name: String = this.typ.getElementName()
	def origin: IType = typ
	
	def methods(): Array[RSMethod] = {
		return typ.getMethods().map(e => new RSMethod(e))
	}
	
	// Get method whose signature is matched
	def method(name: String, signature: Array[String]): RSMethod = {
		return typ.getMethod(name, signature)
	}
	
	// ‚à‚µ‚àCcls.methods(RSParams("modifier" -> Array("public"))) ‚Æ‚©‚µ‚½‚¯‚ê‚ÎCˆÈ‰º‚ð—LŒø‚É‚·‚é
	def methods(params: RSParam[_]*): Array[RSMethod] = {
		return this.methods().where(params.toArray)
	}
	
	
	// Get instance / class fields
	def fields() : Array[RSField] = {
		return this.typ.getFields().map(e => new RSField(e))
	}
	
	override def getDeclaration(): TypeDeclaration = {
		var cu = ASTUtil.createAST(typ.getCompilationUnit()).asInstanceOf[CompilationUnit]
		var dec = ASTNodeSearchUtil.getTypeDeclarationNode(typ, cu)
		return dec
	}

}