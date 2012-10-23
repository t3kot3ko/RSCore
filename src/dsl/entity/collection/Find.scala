package dsl.entity.collection
import dsl.query.RSQuery

trait Find[T]{
	def select(query: RSQuery): Array[T]
	def exclude(query: RSQuery): Array[T]
}