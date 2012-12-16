package rscore.dsl.entity
import org.eclipse.jdt.core.IMethod
import org.eclipse.jdt.core.Signature
import org.eclipse.jdt.core.dom.MethodDeclaration
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
import org.eclipse.jdt.core.dom.Modifier
import rscore.dsl.util.ASTUtil
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.core.dom.Type
import rscore.dsl.traits.search.ModifierBasedSearchable
import rscore.dsl.traits.search.NameBasedSearchable
import rscore.dsl.traits.search.CallbackBasedSearchable
import rscore.dsl.traits.search.TypeBasedSearchable
import rscore.dsl.traits.search.SignatureBasedSearchable
import org.eclipse.jdt.core.IType
import rscore.dsl.traits.action.RSTIntroduceFactory
import rscore.dsl.entity.collection.RSCollection
import rscore.dsl.traits.action.RSTIntroduceParameterObject
import rscore.dsl.traits.action.RSTRenameRefactoring
import rscore.dsl.traits.action.RSTIntroduceIndirection
import org.eclipse.jdt.core.dom.Block
import rscore.dsl.detail.RSBody

class RSMethod(element: IMethod)
	// extends RSEntity
	extends RSMember(element)
	with ModifierBasedSearchable
	with NameBasedSearchable
	with TypeBasedSearchable
	with SignatureBasedSearchable
	// with CallbackBasedSearchable[RSMethod]

	// Refactoring traits
	with RSTIntroduceFactory 
	with RSTIntroduceParameterObject
	with RSTRenameRefactoring
	with RSTIntroduceIndirection{

	override val __identifier = "method"
	override val kind = RSEntity.METHOD
		
	override val signature: Array[String] = Signature.getParameterTypes(element.getSignature())
	override val typ: Type = this.declaration.asInstanceOf[MethodDeclaration].getReturnType2()
	override val name: String = element.getElementName()
	
	// override val self = this

	override def origin(): IMethod = element
	
	def localVariables : Any = {
		return null
	}
	
	def body(): RSBody = {
		return new RSBody(this.getDeclaration().getBody(), this)
	}
	
	
	def parameters(): RSCollection[RSParameter] = {
		val ps = this.element.getParameters().map(e => new RSParameter(e))
		return new RSCollection[RSParameter](ps)
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