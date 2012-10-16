package dsl.entity.collection
import dsl.action.RSTarget
import dsl.entity.RSEntity

trait RSCollection[T] {
	val elements: Array[T]
	def all(): Array[T] = {
		return elements
	}
	def toTarget(): RSTarget = {
		return new RSTarget(elements)
	}

	def origin(): Array[_]
}
