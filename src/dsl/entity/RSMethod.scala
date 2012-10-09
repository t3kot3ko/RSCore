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

class RSMethod(val method: IMethod) extends ModifierBasedSearchable with NameBasedSearchable with ReturnTypeSearchable{
	val name = method.getElementName()
	val returnType: Type = this.declaration.asInstanceOf[MethodDeclaration].getReturnType2()
		
	// ñúàÍÅCIMethod Ç™íºê⁄ó~ÇµÇ≠Ç»Ç¡ÇΩéûóp
	def origin: IMethod = method

	

	override def getDeclaration(): MethodDeclaration = {
		var cu = ASTUtil.createAST(method.getCompilationUnit()).asInstanceOf[CompilationUnit]
		var dec: MethodDeclaration = ASTNodeSearchUtil.getMethodDeclarationNode(method, cu)
		return dec
	}
	

	def signatures(): Array[String] = {
		return Signature.getParameterTypes(method.getSignature())
	}

	def ast(): MethodDeclaration = {
		throw new NotImplementedException
	}

}

object RSMethod {
	implicit def convertToRSMethod(method: IMethod): RSMethod = new RSMethod(method)

	def create(method: IMethod): RSMethod = {
		return method.asInstanceOf[RSMethod]
	}
}