package dsl.entity
import org.eclipse.jdt.core.ILocalVariable
import dsl.search_trait.NameBasedSearchable
import dsl.search_trait.SignatureBasedSearchable
import org.eclipse.jdt.core.Signature

/**
 * メソッドパラメータを表すクラス
 * @see ILocalVariables represents a local variable declared in a method or an initializer.
 */
class RSParameter(val element: ILocalVariable)
	extends RSEntity
	with NameBasedSearchable
	// with SignatureBasedSearchable
	{
	
	override val __identifier = "parameter"

	// 変数名
	override val name: String = element.getElementName()
	// シグネチャ ≒ 型名
	// override val signature: Array[String] = Signature.getParameterTypes(element.getTypeSignature())

	override def origin() = element
}