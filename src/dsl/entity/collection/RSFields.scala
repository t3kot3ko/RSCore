package dsl.entity.collection
import org.eclipse.jdt.core.IField
import org.eclipse.jdt.core.dom.Modifier
import dsl.entity.RSField
import dsl.common.RSParams
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import dsl.common.RSParam
import scala.util.matching.Regex

class RSFields(val elements: Array[RSField]) extends Where[RSField] {
	override def where(params: Array[RSParam[_]]): Array[RSField] = {
		return executeWhereQuery(params).toArray[RSField]
	}
	override def whereNot(params: Array[RSParam[_]]): Array[RSField] = {
		return executeWhereNotQuery(params).toArray[RSField]
	}

	override def dispatchWhere(param: RSParam[_]): Set[RSField] = {
		param.value match {
			case ("modifier", modifiers: Array[String]) => return this.elements.filter(e => e.hasModifiersOr(modifiers)).toSet
			case ("name", names: Array[String]) => return this.elements.filter(e => e.hasNamesOr(names)).toSet
			case ("namereg", names: Array[Regex]) => return this.elements.filter(e => e.hasRegexeMathcedNamesOr(names)).toSet
			case ("namereg", names: Array[String]) => return this.elements.filter(e => e.hasRegexeMathcedNamesOr(names)).toSet
			case _ => return this.elements.toSet
		}
	}

	override def dispatchWhereNot(param: RSParam[_]): Set[RSField] = {
		param.value match {
			case ("modifier", modifiers: Array[String]) => return this.elements.filterNot(e => e.hasModifiersOr(modifiers)).toSet
			case ("name", names: Array[String]) => return this.elements.filterNot(e => e.hasNamesOr(names)).toSet
			case ("namereg", names: Array[Regex]) => return this.elements.filterNot(e => e.hasRegexeMathcedNamesOr(names)).toSet
			case ("namereg", names: Array[String]) => return this.elements.filterNot(e => e.hasRegexeMathcedNamesOr(names)).toSet
			case _ => return this.elements.toSet
		}
	}

}

object RSFields {
	implicit def convertToRSField(fields: Array[RSField]) = new RSFields(fields)
}