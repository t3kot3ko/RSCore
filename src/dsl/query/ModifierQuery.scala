package dsl.query
import dsl.entity.collection.Qualifier
import dsl.entity.RSEntity
import dsl.search_trait.ModifierBasedSearchable
import dsl.entity.collection.WithOr
import dsl.entity.collection.WithAnd
import dsl.entity.collection.Without
import dsl.entity.RSField

case class ModifierQuery(q: Qualifier) extends RSQuery(q) {
	override def execute(elements: Array[_ <: RSEntity]): Array[_ <: RSEntity] = {
		elements match {
			case e: Array[ModifierBasedSearchable] =>
				q match {
					case w: WithOr[String] =>
						return elements.filter(e => e.asInstanceOf[ModifierBasedSearchable].hasModifiersOr(w.values.toArray))
					case w: WithAnd[String] =>
						return elements.filter(e => e.asInstanceOf[ModifierBasedSearchable].hasModifiersAnd(w.values.toArray))
					case w: Without[String] =>
						return elements.filterNot(e => e.asInstanceOf[ModifierBasedSearchable].hasModifiersOr(w.values.toArray))
					case _ =>
						return elements
				}
			case _ =>
				return elements

		}
	}

}