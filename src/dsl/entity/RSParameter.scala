package dsl.entity
import org.eclipse.jdt.core.ILocalVariable
import dsl.search_trait.NameBasedSearchable

/**
 * メソッドパラメータを表すクラス
 * @see ILocalVariables represents a local variable declared in a method or an initializer.
 */
class RSParameter(val element: ILocalVariable)
	extends RSEntity[ILocalVariable]
	with NameBasedSearchable {
	
	override val __identifier = "parameter"

	// 変数名
	val name: String = element.getElementName()

	// シグネチャ ≒ 型名
	def signature: String = element.getTypeSignature()

	def hasSignature(signature: String): Boolean = {
		return this.signature == signature
	}
	
	def hasSignaturesOr(signatures: Array[String]): Boolean = {
		return signatures.exists(e => this.hasSignature(e))
	}
}