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
import scala.util.matching.Regex

class RSMethods(val elements: Array[RSMethod]) extends Where[RSMethod]{
	// 実体が欲しくなった時（いずれ消す）
	def origin(): Array[IMethod] = {
		return elements.map(e => e.origin)
	}

	/**
	 * $.methods.where(modifier -> Array("public", "private"), xxx -> Array(1, 2, 3)...)
	 * where に与えられたクエリはすべて AND で解釈される
	 * TODO: extract to somewhere! make trait and apply to other classes
	 */
	def where(params: RSParams): Array[RSMethod] = {
		return executeWhereQuery(params).toArray[RSMethod]
	}

	override def dispatch(param: (String, Array[String])): Set[RSMethod] = {
		param match {
			case ("modifier", modifiers) => return this.elements.filter(e => e.hasModifiersOr(modifiers)).toSet
			case ("return", returnTypes) => return this.elements.filter(e => e.hasReturnTypeNamesOr(returnTypes)).toSet
			case ("name", names) => return this.elements.filter(e => e.hasNamesOr(names)).toSet
			
			// TODO
			// Array[Regex] を混ぜるなら，Array[String], Array[Regex] をラップするクラスが必要かも
			// case ("namereg", names: Array[Regex]) => return this.methods.filter(e => e.hasRegexeMathcedNamesOr(names)).toSet
			case ("namereg", names: Array[String]) => return this.elements.filter(e => e.hasRegexeMathcedNamesOr(names)).toSet
			
			case _ => return this.elements.toSet
		}
	}

	def all(): Array[RSMethod] = {
		return elements
	}

	def privateMethods(): Array[RSMethod] = {
		return elements.filter(e => e.isPrivate())
	}
	
	def first = if (elements.length > 1) elements(0) else null
}

object RSMethods {
	implicit def convertToRSMethods(methods: Array[RSMethod]) = new RSMethods(methods)
}
