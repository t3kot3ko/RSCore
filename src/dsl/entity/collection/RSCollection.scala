package dsl.entity.collection
import dsl.entity.RSEntity

trait RSCollection[T] {
	val elements: Array[T]
	def all(): Array[T] = {
		return elements
	}
	
	def origin(): Array[_]
}