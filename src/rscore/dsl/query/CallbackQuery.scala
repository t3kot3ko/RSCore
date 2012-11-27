package rscore.dsl.query
import rscore.dsl.entity.RSEntity
import rscore.dsl.entity.collection.Qualifier
import rscore.dsl.traits.search.CallbackBasedSearchable
import rscore.dsl.entity.collection.WithOr
import rscore.dsl.entity.collection.WithAnd
import rscore.dsl.entity.collection.Without
import rscore.dsl.traits.search.CallbackBasedSearchable
import rscore.dsl.entity.collection.RSCollection

case class CallbackQuery[T <: RSEntity](q: Qualifier) extends RSQuery(q) {
	override def execute[T <: RSEntity](collection: RSCollection[T]): RSCollection[T] = {
		val elements = collection.rsElements
		val result: Array[T] = elements match {
			case e: Array[CallbackBasedSearchable[T]] =>
				q match {
					case w: WithOr[T => Boolean] =>
						elements.filter(e => e.asInstanceOf[CallbackBasedSearchable[T]].passCallbacksOr(w.values.toArray))
					case w: WithAnd[T => Boolean] =>
						elements.filter(e => e.asInstanceOf[CallbackBasedSearchable[T]].passCallbacksAnd(w.values.toArray))
					case w: Without[T => Boolean] =>
						elements.filterNot(e => e.asInstanceOf[CallbackBasedSearchable[T]].passCallbacksOr(w.values.toArray))
					case _ => elements
				}
			case _ => elements
		}
		return new RSCollection[T](result)
	}

}