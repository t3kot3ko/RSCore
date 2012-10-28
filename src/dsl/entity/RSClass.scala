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
import dsl.traits.search.NameBasedSearchable
import dsl.traits.search.ModifierBasedSearchable
import dsl.traits.search.CallbackBasedSearchable
import dsl.common.RSParam
import dsl.util.ImplicitConversions._

class RSClass(val element: IType)
	extends RSEntity
	with NameBasedSearchable
	with ModifierBasedSearchable
	with CallbackBasedSearchable[RSClass] {

	val __identifier: String = "class"
	val self = this

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

	// Get instance / class fields
	def fields(): Array[RSField] = {
		return this.element.getFields().map(e => new RSField(e))
	}


	override def getDeclaration(): TypeDeclaration = {
		var cu = ASTUtil.createAST(element.getCompilationUnit()).asInstanceOf[CompilationUnit]
		var dec = ASTNodeSearchUtil.getTypeDeclarationNode(element, cu)
		return dec
	}

}
