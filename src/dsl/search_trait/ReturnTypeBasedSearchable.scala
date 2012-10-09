package dsl.search_trait
import org.eclipse.jdt.core.dom.Type

trait ReturnTypeSearchable {
	val returnType: Type
	
	// 返却値型が指定されたものと一致するか（単なる名前比較）
	def hasReturnTypeName(returnTypeName: String): Boolean = {
		return this.returnType.toString() == returnTypeName
	}
	
	// 指定された名前のうち，どれかを持っているもの
	def hasReturnTypeNamesOr(returnTypeNames: Array[String]): Boolean = {
		return returnTypeNames.exists(hasReturnTypeName(_))
	}
	

}