package dsl.query
import dsl.traits.search.NameBasedSearchable
import dsl.entity.collection.Qualifier
import dsl.entity.collection.WithOr
import dsl.entity.collection.Without
import dsl.entity.RSEntity
import dsl.entity.collection.RSCollection

case class NameQuery(val q: Qualifier) extends RSQuery(q) {
	override def execute[T <: RSEntity](collection: RSCollection[T]): RSCollection[T] = {
		val elements = collection.elements
		val result: Array[T] = elements match {
			case e: Array[NameBasedSearchable] =>
				q match {
					case w: WithOr[String] =>
						elements.filter(e => e.asInstanceOf[NameBasedSearchable].hasNamesOr(w.values.toArray))
					case w: Without[String] =>
						elements.filterNot(e => e.asInstanceOf[NameBasedSearchable].hasNamesOr(w.values.toArray))
					case _ =>
						elements
				}
			case _ => elements
		}

		return new RSCollection[T](result)
	}
}
