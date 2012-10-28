package dsl.traits.search
import org.eclipse.jdt.core.dom.Type

/**
 * 型によって検索するためのトレイト
 */
trait TypeBasedSearchable {
	val typ: Type
	
	// 型が指定されたものと一致するか（単なる名前比較）
	def hasTypeName(returnTypeName: String): Boolean = {
		return this.typ.toString() == returnTypeName
	}
	
	// 指定された名前のうち，どれかを持っているもの
	def hasTypeNamesOr(typeNames: Array[String]): Boolean = {
		return typeNames.exists(hasTypeName(_))
	}
}