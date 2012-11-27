package rscore.dsl.query
import rscore.dsl.traits.search.RSSearchTrait
import rscore.dsl.entity.RSEntity
import rscore.dsl.entity.collection.Qualifier
import rscore.dsl.entity.collection.RSCollection

abstract class RSQuery(val qualfier: Qualifier) {
	// to be overridden in subclasses
	def execute[T <: RSEntity](collection: RSCollection[T]): RSCollection[T]
}
