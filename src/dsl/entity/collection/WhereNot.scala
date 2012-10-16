package dsl.entity.collection
import dsl.common.RSParam

trait WhereNot[T]{
	val elements: Array[T]
	
	// -- To be overridden in subclasses
	def dispatchWhereNot(param: RSParam[_]): Set[T]
	
	def executeWhereNotQuery(params: Array[RSParam[_]]): Set[T] = {
		var result = elements.toSet[T]
		//for (param <- params.getValue()) {
		for (param <- params) {
			result = result & dispatchWhereNot(param)
		}
		// return result.toArray[T]
		return result
	}
	def whereNot(params: Array[RSParam[_]]): Array[T]
	def whereNot(params: RSParam[_]*): Array[T] = {
		return whereNot(params.toArray)
	}
}