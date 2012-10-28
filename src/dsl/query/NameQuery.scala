package dsl.query
import dsl.search_trait.NameBasedSearchable
import dsl.entity.collection.Qualifier
import dsl.entity.collection.WithOr
import dsl.entity.collection.Without
import dsl.entity.RSEntity

case class NameQuery(val q: Qualifier) extends RSQuery(q) {
	override def execute(elements: Array[_ <: RSEntity]): Array[_ <: RSEntity] = {
		elements match {
			case e: Array[NameBasedSearchable] =>
				q match {
					case w: WithOr[String] => return elements.filter(e => e.asInstanceOf[NameBasedSearchable].hasNamesOr(w.values.toArray))
					case w: Without[String] => return elements.filterNot(e => e.asInstanceOf[NameBasedSearchable].hasNamesOr(w.values.toArray))
					case _ => return elements
				}
			case _ => return elements
		}
	}
}