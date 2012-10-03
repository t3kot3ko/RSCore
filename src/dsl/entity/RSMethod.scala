package dsl.entity
import org.eclipse.jdt.core.IMethod
import org.eclipse.jdt.core.Signature
import org.eclipse.jdt.core.dom.MethodDeclaration
import sun.reflect.generics.reflectiveObjects.NotImplementedException

class RSMethod(val method: IMethod){
	// ñúàÍÅCIMethod Ç™íºê⁄ó~ÇµÇ≠Ç»Ç¡ÇΩéûóp
	def origin: IMethod = method
	
	def name(): String = {
		return method.getElementName()
	}
	
	def signatures() : Array[String] = {
		return Signature.getParameterTypes(method.getSignature())
	}
	
	def ast() : MethodDeclaration = {
		throw new NotImplementedException
	}

}

object RSMethod{
	implicit def convertToRSMethod(method: IMethod): RSMethod = new RSMethod(method)
	
	def create(method: IMethod): RSMethod = {
		return method.asInstanceOf[RSMethod]
	}
}