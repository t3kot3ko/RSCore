package dsl.entity.collection
import org.eclipse.jdt.core.IField
import org.eclipse.jdt.core.dom.Modifier
import dsl.entity.RSField
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import dsl.common.RSParam
import scala.util.matching.Regex
import dsl.target.RSTarget
import dsl.query.RSQuery
import dsl.query.NameQuery
import dsl.query.ModifierQuery
import dsl.query.NameRegQuery

class RSFields(val elements: Array[RSField])
	extends RSCollection[RSField] with Find[RSField]{

	override def origin: Array[IField] = elements.map(_.origin)
	def toTarget(): Array[RSTarget] = this.elements.map(e => e.toTarget())
	
	// TODO: extract
	override def select(query: RSQuery): Array[RSField] = {
		query match{
			case NameQuery(names: Array[String]) => this.elements.filter(e => e.hasNamesOr(names))
			case NameRegQuery(nameregs: Array[String]) => this.elements.filter(e => e.hasRegexeMathcedNamesOr(nameregs))
			case ModifierQuery(modifiers: Array[String]) => return this.elements.filter(e => e.hasModifiersOr(modifiers))
			case _ => return this.elements
		}
	}
	override def exclude(query: RSQuery) : Array[RSField] = {
		query match{
			case NameQuery(names: Array[String]) => this.elements.filterNot(e => e.hasNamesOr(names))
			case NameRegQuery(nameregs: Array[String]) => this.elements.filterNot(e => e.hasRegexeMathcedNamesOr(nameregs))
			case ModifierQuery(modifiers: Array[String]) => return this.elements.filterNot(e => e.hasModifiersOr(modifiers))
			case _ => return this.elements
		}
	}

}
