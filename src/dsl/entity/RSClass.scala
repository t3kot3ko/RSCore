package dsl.entity
import org.eclipse.jdt.core.IType
import org.eclipse.jdt.core.dom.ASTNode
import dsl.util.ASTUtil
import org.eclipse.jdt.core.dom.CompilationUnit
import scala.collection.JavaConverters._
import org.eclipse.jdt.core.dom.TypeDeclaration
import org.eclipse.jdt.core.dom.FieldDeclaration
import org.eclipse.jdt.core.dom.VariableDeclaration
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
import org.eclipse.jdt.core.IMethod
import org.eclipse.jdt.core.dom.Modifier
import dsl.entity.collection.RSMethods
import dsl.search_trait.NameBasedSearchable
import dsl.search_trait.ModifierBasedSearchable
import dsl.search_trait.CallbackBasedSearchable
import dsl.common.RSParam
import dsl.action.RSTarget
import dsl.util.ImplicitConversions._

class RSClass(val element: IType)
	extends RSEntity[IType]
	with NameBasedSearchable
	with ModifierBasedSearchable
	with CallbackBasedSearchable[RSClass]{

	val name: String = this.element.getElementName()
	override def origin: IType = element
	// override def toTarget(): RSTarget = new RSTarget(Array(element))

	def methods(): Array[RSMethod] = {
		return element.getMethods().map(e => new RSMethod(e))
	}

	def isInterface(): Boolean = return this.element.isInterface()
	def isClass(): Boolean = return this.element.isClass()
	def kind: String = if (this.isClass) "class" else "interface"
	val superclassName = this.element.getSuperclassName()

	// Get method whose signature is matched
	def method(name: String, signature: Array[String]): RSMethod = {
		return element.getMethod(name, signature)
	}

	// ‚à‚µ‚àCcls.methods(RSParams("modifier" -> Array("public"))) ‚Æ‚©‚µ‚½‚¯‚ê‚ÎCˆÈ‰º‚ð—LŒø‚É‚·‚é
	def methods(params: RSParam[_]*): Array[RSMethod] = {
		return this.methods().where(params.toArray)
	}

	// Get instance / class fields
	def fields(): Array[RSField] = {
		return this.element.getFields().map(e => new RSField(e))
	}
	
	def passCallback(callback: RSClass => Boolean): Boolean = {
		return callback(this)
	}
	
	def passCallbacksOr(callbacks: Array[RSClass => Boolean]): Boolean = {
		return callbacks.exists(e => e(this))
	}
	def passCallbacksAnd(callbacks: Array[RSClass => Boolean]): Boolean = {
		return callbacks.forall(e => e(this))
	}
	

	override def getDeclaration(): TypeDeclaration = {
		var cu = ASTUtil.createAST(element.getCompilationUnit()).asInstanceOf[CompilationUnit]
		var dec = ASTNodeSearchUtil.getTypeDeclarationNode(element, cu)
		return dec
	}

}
