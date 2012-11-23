package dsl.query
import dsl.entity.collection.Qualifier
import dsl.entity.RSEntity
import dsl.traits.search.ModifierBasedSearchable
import dsl.entity.collection.WithOr
import dsl.entity.collection.WithAnd
import dsl.entity.collection.Without
import dsl.entity.RSField
import dsl.entity.collection.RSCollection

case class ModifierQuery(q: Qualifier) extends RSQuery(q) {
	override def execute[T <: RSEntity](collection: RSCollection[T]): RSCollection[T] = {
		val elements = collection.elements
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