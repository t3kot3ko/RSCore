package dsl.entity
import org.eclipse.jdt.core.IMethod
import org.eclipse.jdt.core.Signature
import org.eclipse.jdt.core.dom.MethodDeclaration

class RSMethod extends IMethod{
	def name(): String = {
		return this.getElementName()
	}
	
	def signatures() : Array[String] = {
		return Signature.getParameterTypes(getSignature())
	}
	
	def ast() : MethodDeclaration = {
	}

}

object RSMethod{
	def create(method: IMethod): RSMethod = {
		return method.asInstanceOf[RSMethod]
	}
}