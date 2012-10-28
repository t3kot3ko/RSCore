package dsl.query
import scala.util.matching.Regex
import dsl.entity.collection.Qualifier
import dsl.entity.RSEntity
import dsl.search_trait.NameBasedSearchable
import dsl.entity.collection.WithOr
import dsl.entity.collection.Without

case class NameRegQuery(q: Qualifier) extends RSQuery(q){
	override def execute(elements: Array[_ <: RSEntity]): Array[_ <: RSEntity] = {
		elements match {
			case e: Array[NameBasedSearchable] =>
				q match {
					case w: WithOr[String] => return elements.filter(e => e.asInstanceOf[NameBasedSearchable].hasRegexeMathcedNamesOr(w.values.toArray))
					case w: Without[String] => return elements.filterNot(e => e.asInstanceOf[NameBasedSearchable].hasRegexeMathcedNamesOr(w.values.toArray))
					case _ => return elements
				}
			case _ => return elements
		}
		
	}

}