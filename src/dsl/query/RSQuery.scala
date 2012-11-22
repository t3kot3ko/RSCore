package dsl.query
import dsl.traits.search.RSSearchTrait
import dsl.entity.RSEntity
import dsl.entity.collection.Qualifier
import dsl.entity.collection.RSCollection

abstract class RSQuery(val qualfier: Qualifier) {
	// to be overridden in subclasses
	def execute(elements: Array[_ <: RSEntity]): Array[_ <: RSEntity]
	def execute[T <: RSEntity](collection: RSCollection[T]): RSCollection[T]
}