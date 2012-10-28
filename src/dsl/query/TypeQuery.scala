package dsl.query
import dsl.entity.collection.Qualifier
import dsl.entity.RSEntity
import dsl.entity.collection.WithOr
import dsl.entity.collection.Without
import dsl.search_trait.TypeBasedSearchable

case class TypeQuery(val q: Qualifier) extends RSQuery(q){
	override def execute(elements: Array[_ <: RSEntity]): Array[_ <: RSEntity] = {
		elements match {
			case e: Array[TypeBasedSearchable] =>
				q match {
					case w: WithOr[String] => return elements.filter(e => e.asInstanceOf[TypeBasedSearchable].hasTypeNamesOr(w.values.toArray))
					case w: Without[String] => return elements.filterNot(e => e.asInstanceOf[TypeBasedSearchable].hasTypeNamesOr(w.values.toArray))
					case _ => return elements
				}
			case _ => return elements
		}
		
	}

}