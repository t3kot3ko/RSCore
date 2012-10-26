package dsl.query
import dsl.entity.RSEntity
import dsl.entity.collection.Qualifier
import dsl.search_trait.CallbackBasedSearchable
import dsl.entity.collection.With
import dsl.entity.collection.WithAnd
import dsl.entity.collection.Without
import dsl.search_trait.CallbackBasedSearchable

case class CallbackQuery[T <: RSEntity](q: Qualifier) extends RSQuery(q) {
	def execute(elements: Array[_ <: RSEntity]): Array[_ <: RSEntity] = {
		elements match {
			case e: Array[CallbackBasedSearchable[T]] =>
				q match {
					case w: With[T => Boolean] => 
						return elements.filter(e => e.asInstanceOf[CallbackBasedSearchable[T]].passCallbacksOr(w.values.toArray))
					case w: WithAnd[T => Boolean] => 
						return elements.filter(e => e.asInstanceOf[CallbackBasedSearchable[T]].passCallbacksAnd(w.values.toArray))
					case w: Without[T => Boolean] =>
						return elements.filterNot(e => e.asInstanceOf[CallbackBasedSearchable[T]].passCallbacksOr(w.values.toArray))
					case _ => return elements
				}
			case _ => return elements
		}
	}

}