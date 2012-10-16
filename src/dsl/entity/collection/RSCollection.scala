package dsl.entity.collection
import dsl.action.RSTarget
import dsl.entity.RSEntity

trait RSCollection[T] {
	val elements: Array[T]
	def all(): Array[T] = elements
	// def toTarget(): RSTarget
	def origin: Array[_]
}
