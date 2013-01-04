package rscore.rewriter
import org.eclipse.jface.text.Document
import rscore.dsl.entity.RSMethod
import rscore.dsl.util.ASTUtil
import org.eclipse.jdt.core.dom.MethodDeclaration
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
import org.eclipse.jdt.core.dom.SimpleName

/**
 * RSMethod に属すプロパティを書き換える（リファクタリングではない）Rewriter
 */
class RSMethodRewriter(m: RSMethod) extends AbstractRewriter(m){
	override val dec: MethodDeclaration = ASTNodeSearchUtil.getMethodDeclarationNode(m.origin, cu)

	/**
	 * アクセス修飾子を変更する
	 * TODO: 文字列からの変換，複数指定
	 */
	def changeModifier(newModifier: Int): Unit = {
		this.dec.setModifiers(newModifier)
	}
	
	/**
	 * 名前を変更する
	 * リファクタリングではないので，参照関係は一切チェックしない
	 */
	def changeName(newName: String): Unit = {
		this.dec.setName(this.cu.getAST().newSimpleName(newName))
	}
}