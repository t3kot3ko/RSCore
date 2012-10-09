package dsl.entity.collection
import dsl.common.RSParams

/**
 * where() 等の検索をできるようにするトレイト
 * 型変換がうまくいかないので，実装クラスで toArray をかける
 */
trait Where[T]{
	val elements: Array[T]
	def dispatch(param: (String, Array[String])): Set[T]
 
 	def executeWhereQuery(params: RSParams): Set[T] = {
		var result = elements.toSet[T]
		for (param <- params.getValue()) {
			result = result & dispatch(param)
		}
		// return result.toArray[T]
		return result
	}
	

}