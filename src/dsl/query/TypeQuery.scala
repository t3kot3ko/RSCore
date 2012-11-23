package dsl.query
import dsl.entity.collection.Qualifier
import dsl.entity.RSEntity
import dsl.entity.collection.WithOr
import dsl.entity.collection.Without
import dsl.traits.search.TypeBasedSearchable
import dsl.entity.collection.RSCollection

case class TypeQuery(val q: Qualifier) extends RSQuery(q){
	override def execute[T <: RSEntity](collection: RSCollection[T]): RSCollection[T] = {
		val elements = collection.elements
		val result: Array[T] = elements match {
			case e: Array[TypeBasedSearchable] =>
				q match {
					case w: WithOr[String] => elements.filter(e => e.asInstanceOf[TypeBasedSearchable].hasTypeNamesOr(w.values.toArray))
					case w: Without[String] => elements.filterNot(e => e.asInstanceOf[TypeBasedSearchable].hasTypeNamesOr(w.values.toArray))
					case _ => elements
				}
			case _ => elements
		}
		return new RSCollection[T](result)
		
	}

}