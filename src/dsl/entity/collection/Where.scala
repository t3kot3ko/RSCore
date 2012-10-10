package dsl.entity.collection
import dsl.common.RSParams
import dsl.common.RSParam

/**
 * where() 等の検索をできるようにするトレイト
 * 型変換がうまくいかないので，実装クラスで toArray をかける
 */
trait Where[T]{
	val elements: Array[T]
	// def dispatchWhere(param: (String, Array[String])): Set[T]
	// def dispatchWhereNot(param: (String, Array[String])): Set[T]
	def dispatchWhere(param: RSParam[_]): Set[T]
	def dispatchWhereNot(param: RSParam[_]): Set[T]
 
 	def executeWhereQuery(params: Array[RSParam[_]]): Set[T] = {
		var result = elements.toSet[T]
		for (param <- params) {
			result = result & dispatchWhere(param)
		}
		// return result.toArray[T]
		return result
	}
	
	def executeWhereNotQuery(params: Array[RSParam[_]]): Set[T] = {
		var result = elements.toSet[T]
		//for (param <- params.getValue()) {
		for (param <- params) {
			result = result & dispatchWhereNot(param)
		}
		// return result.toArray[T]
		return result
	}
	

}