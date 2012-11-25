package dsl.entity.collection
import dsl.entity.RSEntity
import dsl.query.RSQuery
import dsl.query.NameQuery
import dsl.entity.RSField
import scala.collection.mutable.ArraySeq
import dsl.query.NameRegQuery
import dsl.query.ModifierQuery
import dsl.common.RSObject

case class RSCollection[T <: RSEntity](elements: Array[T]) extends RSObject{
	// def this(elements: ArraySeq[T]) = this(elements.toArray[T])
	// val elements: Array[T]
	def all(): Array[T] = elements
	// def toTarget(): RSTarget
	def origin: Array[T] = elements
	def first(): T = elements.first
	
	// コレクションの要素数を取得する3つの関数（2つはエイリアス）
	def length: Int = elements.length
	def size = length
	def count = length
	
	def isEmpty: Boolean = this.length == 0
	def notFound: Boolean = isEmpty
	def isNotEmpty: Boolean = !isEmpty
	def found: Boolean = isNotEmpty
	
	def apply(index: Int): T = elements(index)

	/**
	 * コレクションからクエリにマッチするオブジェクトを検索する
	 */
	def select(query: RSQuery): RSCollection[T] = {
		var result = query.execute(this)
		return result
		// val a = abstractArray.map(_.asInstanceOf[T])
	}
	
	// Just an alias to select
	def my_select(query: RSQuery): RSCollection[T] = {
		return this.select(query)
	}

}

