package dsl.entity.collection

trait RSCollection[T] {
	val elements: Array[T]
	def all(): Array[T] = {
		return elements
	}

}