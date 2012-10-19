package dsl.entity
import org.eclipse.jdt.core.IMethod
import org.eclipse.jdt.core.Signature
import org.eclipse.jdt.core.dom.MethodDeclaration
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import dsl.entity.collection.RSMethods
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
import org.eclipse.jdt.core.dom.Modifier
import dsl.util.ASTUtil
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.core.dom.Type
import dsl.search_trait.ModifierBasedSearchable
import dsl.search_trait.NameBasedSearchable
import dsl.search_trait.ReturnTypeSearchable
import dsl.search_trait.CallbackBasedSearchable

class RSMethod(val element: IMethod)
	extends RSEntity[IMethod]
	with ModifierBasedSearchable
	with NameBasedSearchable
	with ReturnTypeSearchable
	with CallbackBasedSearchable[RSMethod]{
	
	override val __identifier = "method"

	val name = element.getElementName()
	val returnType: Type = this.declaration.asInstanceOf[MethodDeclaration].getReturnType2()
	
	def parameters(): Array[RSParameter] = {
		this.element.getParameters().map(e => new RSParameter(e))
	}
	override def origin(): IMethod = element
	// override def toTarget : RSTarget = new RSTarget(Array(element))
	
	override def getDeclaration(): MethodDeclaration = {
		var cu = ASTUtil.createAST(element.getCompilationUnit()).asInstanceOf[CompilationUnit]
		var dec: MethodDeclaration = ASTNodeSearchUtil.getMethodDeclarationNode(element, cu)
		return dec
	}
	def passCallback(callback: (RSMethod => Boolean)): Boolean = callback(this)
	def passCallbacksOr(callbacks: Array[RSMethod => Boolean]): Boolean = {
		return callbacks.exists(cb => cb(this))
	}
	def passCallbacksAnd(callbacks: Array[RSMethod => Boolean]): Boolean = {
		return callbacks.forall(cb => cb(this))
	}
	
	// —áŠO‚Ì–¼‘O
	def exceptionTypes: Array[String] = this.element.getExceptionTypes()

	def signatures(): Array[String] = {
		return Signature.getParameterTypes(element.getSignature())
	}
}