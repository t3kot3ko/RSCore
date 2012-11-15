package dsl.entity.collection
import dsl.entity.RSEntity
import dsl.query.RSQuery
import dsl.query.NameQuery
import dsl.entity.RSField
import scala.collection.mutable.ArraySeq
import dsl.query.NameRegQuery
import dsl.query.ModifierQuery
import dsl.common.RSObject

abstract class RSCollection[T <: RSEntity] extends RSObject {

	val elements: Array[T]
	def all(): Array[T] = elements
	// def toTarget(): RSTarget
	def origin: Array[_]
	// def first() : T = elements.first

	/**
	 * コレクションからクエリにマッチするオブジェクトを検索する
	 */
	def selects(query: RSQuery): ArraySeq[T] = {
		var abstractArray = query.execute(this.elements)
		return abstractArray.map(_.asInstanceOf[T])
	}

}
