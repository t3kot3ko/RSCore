package rscore.dsl.query
import rscore.dsl.entity.collection.Qualifier
import rscore.dsl.entity.RSEntity
import rscore.dsl.entity.collection.WithOr
import rscore.dsl.entity.collection.Without
import rscore.dsl.traits.search.TypeBasedSearchable
import rscore.dsl.entity.collection.RSCollection

case class TypeQuery(val q: Qualifier) extends RSQuery(q){
	override def execute[T <: RSEntity](collection: RSCollection[T]): RSCollection[T] = {
		val elements = collection.rsElements
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