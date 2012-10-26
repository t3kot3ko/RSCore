package dsl.query
import dsl.search_trait.RSSearchTrait
import dsl.entity.RSEntity
import dsl.entity.collection.Qualifier

abstract class RSQuery(val qualfier: Qualifier) {
	// to be overridden in subclasses
	def execute(elements: Array[_ <: RSEntity]): Array[_ <: RSEntity]
}