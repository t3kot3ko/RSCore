package dsl.entity.collection
import org.eclipse.jdt.core.IMethod
import dsl.entity.RSMethod

class RSMethods(val methods: Array[RSMethod]){
	
	def all(): Array[RSMethod] = {
		return methods
	}
	
	def privateMethods: Array[RSMethod] = {
		
	}
	
}

object RSMethods{
	implicit def convertToRSMethods(methods: Array[RSMethod]) = new RSMethods(methods)
}
