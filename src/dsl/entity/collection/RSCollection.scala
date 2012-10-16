package dsl.entity.collection
import dsl.action.RSTarget

trait RSCollection[T] {
	val elements: Array[T]
	def all(): Array[T] = {
		return elements
	}

	def toTarget(): RSTarget = {
		return new RSTarget(elements)
	}

}