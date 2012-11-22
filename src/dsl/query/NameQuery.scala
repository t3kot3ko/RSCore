package dsl.query
import dsl.traits.search.NameBasedSearchable
import dsl.entity.collection.Qualifier
import dsl.entity.collection.WithOr
import dsl.entity.collection.Without
import dsl.entity.RSEntity
import dsl.entity.collection.RSCollection

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
	
	// TODO: remove bugs for non-using implicit conversions
	def execute[T <: RSEntity](collection: RSCollection[T]): Array[_ <: RSEntity] = {
		val elements = collection.elements;
		return this.execute(elements)
	}
}