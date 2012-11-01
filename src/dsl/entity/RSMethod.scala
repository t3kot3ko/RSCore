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
import dsl.traits.search.ModifierBasedSearchable
import dsl.traits.search.NameBasedSearchable
import dsl.traits.search.CallbackBasedSearchable
import dsl.traits.search.TypeBasedSearchable
import dsl.traits.search.SignatureBasedSearchable
import org.eclipse.jdt.core.IType
import dsl.traits.action.RSTIntroduceFactory

class RSMethod(val element: IMethod)
	extends RSEntity
	with ModifierBasedSearchable
	with NameBasedSearchable
	with TypeBasedSearchable
	with SignatureBasedSearchable
	with CallbackBasedSearchable[RSMethod]

	with RSTIntroduceFactory {

	override val __identifier = "method"
		
	override val signature: Array[String] = Signature.getParameterTypes(element.getSignature())
	override val typ: Type = this.declaration.asInstanceOf[MethodDeclaration].getReturnType2()
	override val name: String = element.getElementName()
	
	val self = this

	override def origin(): IMethod = element
	def parameters(): Array[RSParameter] = {
		this.element.getParameters().map(e => new RSParameter(e))
	}

	override def getDeclaration(): MethodDeclaration = {
		var cu = ASTUtil.createAST(element.getCompilationUnit()).asInstanceOf[CompilationUnit]
		var dec: MethodDeclaration = ASTNodeSearchUtil.getMethodDeclarationNode(element, cu)
		return dec
	}
	def exceptionTypes: Array[String] = this.element.getExceptionTypes()
	
	def isConstructor(): Boolean = {
		return element.getParent().asInstanceOf[IType].getElementName() == this.name
	}

}