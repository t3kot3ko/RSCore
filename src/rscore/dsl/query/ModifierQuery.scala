package rscore.dsl.query
import rscore.dsl.entity.collection.Qualifier
import rscore.dsl.entity.RSEntity
import rscore.dsl.traits.search.ModifierBasedSearchable
import rscore.dsl.entity.collection.WithOr
import rscore.dsl.entity.collection.WithAnd
import rscore.dsl.entity.collection.Without
import rscore.dsl.entity.RSField
import rscore.dsl.entity.collection.RSCollection

case class ModifierQuery(q: Qualifier) extends RSQuery(q) {
	override def execute[T <: RSEntity](collection: RSCollection[T]): RSCollection[T] = {
		val elements = collection.rsElements
		val result: Array[T] = elements match {
			case e: Array[ModifierBasedSearchable] =>
				q match {
					case w: WithOr[String] => elements.filter(e => e.asInstanceOf[ModifierBasedSearchable].hasModifiersOr(w.values.toArray))
					case w: WithAnd[String] => elements.filter(e => e.asInstanceOf[ModifierBasedSearchable].hasModifiersAnd(w.values.toArray))
					case w: Without[String] => elements.filterNot(e => e.asInstanceOf[ModifierBasedSearchable].hasModifiersOr(w.values.toArray))
					case _ => elements
				}
			case _ => elements
		}
		return new RSCollection[T](result)
	}

}