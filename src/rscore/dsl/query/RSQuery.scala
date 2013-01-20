package rscore.dsl.query
import rscore.dsl.traits.search.RSSearchTrait
import rscore.dsl.entity.RSEntity
import rscore.dsl.entity.collection.Qualifier
import rscore.dsl.entity.collection.RSCollection
import rscore.dsl.exception.EntityNotFoundException

abstract class RSQuery(val qualfier: Qualifier) {
	// to be overridden in subclasses
	def execute[T <: RSEntity](collection: RSCollection[T]): RSCollection[T]

	/**
	 * 先頭の要素1つだけ取り出す（要素が明らかに1つだとわかるようなときに使う）
	 * TODO: 今は，TemplateMethod パターンだが，パフォーマンス悪いのでいつか直す
	 */
	def executeOne[T <: RSEntity](collection: RSCollection[T]): T = {
		val r = this.execute(collection)
		if(!r.found){
			throw new EntityNotFoundException
		}
		return r.first
	}
}
