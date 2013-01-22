package rscore.dsl.entity
import org.eclipse.jdt.core.ILocalVariable
import rscore.dsl.traits.search.NameBasedSearchable
import rscore.dsl.traits.search.SignatureBasedSearchable
import org.eclipse.jdt.core.Signature
import rscore.dsl.traits.action.RSTRenameRefactoring

/**
 * メソッドパラメータを表すクラス
 * @see ILocalVariables represents a local variable declared in a method or an initializer.
 */
class RSParameter(val element: ILocalVariable, val parent: RSMethod = null)
	extends RSEntity
	with NameBasedSearchable
	with RSTRenameRefactoring
	// with SignatureBasedSearchable
	{
	
	override val kind = RSEntity.PARAMETER
	override val __identifier = "parameter"
	override val self = this

	// 変数名
	override val name: String = element.getElementName()
	// シグネチャ ≒ 型名
	// override val signature: Array[String] = Signature.getParameterTypes(element.getTypeSignature())

	override def origin() = element
}