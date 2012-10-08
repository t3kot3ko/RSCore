package dsl.entity.collection
import org.eclipse.jdt.core.IMethod
import scala.collection.JavaConversions._
import dsl.entity.RSMethod
import dsl.util.ASTUtil
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
import org.eclipse.jdt.core.dom.MethodDeclaration
import scala.collection.mutable.Buffer
import org.eclipse.jdt.core.dom.Modifier
import scala.collection.mutable.ListBuffer
import dsl.common.RSParams
import scala.collection.mutable.LinkedList
class RSMethods(val methods: Array[RSMethod]) {
	// ŽÀ‘Ì‚ª—~‚µ‚­‚È‚Á‚½Žži‚¢‚¸‚êÁ‚·j
	def origin(): Array[IMethod] = {
		return methods.map(e => e.origin)
	}

	/**
	 * $.methods.where(modifier -> Array("public", "private"), xxx -> Array(1, 2, 3)...)
	 */
	def where(params: RSParams): Array[RSMethod] = {
		var result = methods.toSet[RSMethod]
		// var list = List[RSMethod]()
		for (param <- params.getValue()) {
			result = result & dispatch(param)
		}
		return result.toArray[RSMethod]
	}

	private def dispatch(param: (String, Array[String])): Set[RSMethod] = {
		param match {
			case ("modifier", modifiers) => return this.methods.filter(e => e.hasModifiersOr(modifiers)).toSet
			// case ("return", returnTypes) => return this.methods.toSet
			case ("return", returnTypes) => return this.methods.filter(e => e.hasReturnTypeNamesOr(returnTypes)).toSet
			case _ => return this.methods.toSet
		}
	}

	def all(): Array[RSMethod] = {
		return methods
	}

	def privateMethods(): Array[RSMethod] = {
		return methods.filter(e => e.isPrivate())
	}
	
	def first = if (methods.length > 1) methods(0) else null
}

object RSMethods {
	implicit def convertToRSMethods(methods: Array[RSMethod]) = new RSMethods(methods)
}
