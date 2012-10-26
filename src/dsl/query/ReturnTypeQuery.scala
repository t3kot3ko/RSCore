package dsl.query
import dsl.entity.collection.Qualifier
import dsl.entity.RSEntity
import dsl.search_trait.ReturnTypeBasedSearchable
import dsl.entity.collection.With
import dsl.entity.collection.Without
import dsl.search_trait.ReturnTypeBasedSearchable
import dsl.search_trait.ReturnTypeBasedSearchable

case class ReturnTypeQuery(val q: Qualifier) extends RSQuery(q){
	override def execute(elements: Array[_ <: RSEntity]): Array[_ <: RSEntity] = {
		elements match {
			case e: Array[ReturnTypeBasedSearchable] =>
				q match {
					case w: With[String] => return elements.filter(e => e.asInstanceOf[ReturnTypeBasedSearchable].hasReturnTypeNamesOr(w.values.toArray))
					case w: Without[String] => return elements.filterNot(e => e.asInstanceOf[ReturnTypeBasedSearchable].hasReturnTypeNamesOr(w.values.toArray))
					case _ => return elements
				}
			case _ => return elements
		}
		
	}

}