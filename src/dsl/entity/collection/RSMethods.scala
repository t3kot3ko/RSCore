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
import scala.collection.mutable.LinkedList
import scala.util.matching.Regex
import dsl.common.RSParam

class RSMethods(val elements: Array[RSMethod])
	extends RSCollection[RSMethod]
	with Where[RSMethod]
	with WhereNot[RSMethod] {

	// すべての要素を返す
	override def all(): Array[RSMethod] = elements

	// 実体が欲しくなった時（いずれ消す）
	override def origin(): Array[IMethod] = elements.map(e => e.origin)

	/**
	 * $.methods.where(modifier -> Array("public", "private"), xxx -> Array(1, 2, 3)...)
	 * where に与えられたクエリはすべて AND で解釈される
	 */
	override def where(params: Array[RSParam[_]]): Array[RSMethod] = {
		return executeWhereQuery(params).toArray[RSMethod]
	}

	override def dispatchWhere(param: RSParam[_]): Set[RSMethod] = {
		param.value match {
			case ("modifier", modifiers: Array[String]) =>
				return this.elements.filter(e => e.hasModifiersOr(modifiers)).toSet
			case ("return", returnTypes: Array[String]) =>
				return this.elements.filter(e => e.hasReturnTypeNamesOr(returnTypes)).toSet
			case ("name", names: Array[String]) =>
				return this.elements.filter(e => e.hasNamesOr(names)).toSet
			case ("namereg", names: Array[Regex]) =>
				return this.elements.filter(e => e.hasRegexeMathcedNamesOr(names)).toSet
			case ("namereg", names: Array[String]) =>
				return this.elements.filter(e => e.hasRegexeMathcedNamesOr(names)).toSet
			case _ => return this.elements.toSet
		}
	}

	/**
	 * params を「満たさない」要素を返す
	 */
	override def whereNot(params: Array[RSParam[_]]): Array[RSMethod] = {
		return executeWhereNotQuery(params).toArray[RSMethod]
	}

	override def dispatchWhereNot(param: RSParam[_]): Set[RSMethod] = {
		param.value match {
			case ("modifier", modifiers: Array[String]) =>
				return this.elements.filterNot(e => e.hasModifiersOr(modifiers)).toSet
			case ("return", returnTypes: Array[String]) =>
				return this.elements.filterNot(e => e.hasReturnTypeNamesOr(returnTypes)).toSet
			case ("name", names: Array[String]) =>
				return this.elements.filterNot(e => e.hasNamesOr(names)).toSet
			case ("namereg", names: Array[String]) =>
				return this.elements.filterNot(e => e.hasRegexeMathcedNamesOr(names)).toSet
			case ("namereg", names: Array[Regex]) =>
				return this.elements.filterNot(e => e.hasRegexeMathcedNamesOr(names)).toSet
			case _ => return this.elements.toSet
		}
	}

	def privateMethods(): Array[RSMethod] = {
		return elements.filter(e => e.isPrivate())
	}

	def first = if (elements.length > 1) elements(0) else null
}

object RSMethods {
	// implicit def convertToRSMethods(methods: Array[RSMethod]) = new RSMethods(methods)
}
