package dsl.entity.collection
import org.eclipse.jdt.core.IField
import org.eclipse.jdt.core.dom.Modifier
import dsl.entity.RSField
import dsl.common.RSParams

class RSFields(val elements: Array[RSField]) extends Where[RSField]{
	/*
	def where(modifier: String): Array[RSField] = {
		modifier match{
			case "private" => elements.filter(e => e.isPrivate())
		}
		
	}
	*/
	
	def where(params: RSParams): Array[RSField] = {
		return executeWhereQuery(params).toArray[RSField]
	}
	override def dispatch(param: (String, Array[String])): Set[RSField] = {
		param match {
			case ("modifier", modifiers) => return this.elements.filter(e => e.hasModifiersOr(modifiers)).toSet
			case ("name", names) => return this.elements.filter(e => e.hasNamesOr(names)).toSet
			
			// TODO
			// Array[Regex] を混ぜるなら，Array[String], Array[Regex] をラップするクラスが必要かも
			// case ("namereg", names: Array[Regex]) => return this.methods.filter(e => e.hasRegexeMathcedNamesOr(names)).toSet
			case ("namereg", names: Array[String]) => return this.elements.filter(e => e.hasRegexeMathcedNamesOr(names)).toSet
			
			case _ => return this.elements.toSet
		}
	}

}

object RSFields{
	implicit def convertToRSField(fields: Array[RSField]) = new RSFields(fields)
}