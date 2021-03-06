package rscore.dsl.entity
import org.eclipse.jdt.core.IField
import rscore.dsl.util.ASTUtil
import org.eclipse.jdt.core.dom.MethodDeclaration
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
import org.eclipse.jdt.core.dom.Modifier
import scala.collection.JavaConversions._
import org.eclipse.jdt.core.dom.VariableDeclarationFragment
import org.eclipse.jdt.core.dom.FieldDeclaration
import rscore.dsl.traits.search.ModifierBasedSearchable
import rscore.dsl.traits.search.NameBasedSearchable
import org.eclipse.jdt.core.dom.Type
import rscore.dsl.traits.search.TypeBasedSearchable
import rscore.dsl.traits.action.RSTRenameRefactoring
import rscore.dsl.traits.action.RSTSelfEncapsulateFieldRefactoring
import rscore.dsl.traits.action.RSTInlineRefactoring

class RSField(element: IField)
	extends RSMember(element)
	with ModifierBasedSearchable
	with NameBasedSearchable
	with TypeBasedSearchable
	
	// Refactoring traits
	with RSTRenameRefactoring
	with RSTSelfEncapsulateFieldRefactoring
	with RSTInlineRefactoring{

	override val __identifier = "field"
	override val kind = RSEntity.FIELD
	
	override val typ: Type = this.getDeclaration().getType()
	override val self: RSField = this

	// TODO: ワンライナだと長いから．
	val name: String = {
		() =>
			{
				var dec = getDeclaration()
				dec.fragments().first.asInstanceOf[VariableDeclarationFragment].getName().toString()
			}
	}.apply()

	override def origin: IField = element

	// 自分自身の定義をASTから探す
	override def getDeclaration(): FieldDeclaration = {
		var cu = ASTUtil.createAST(element.getCompilationUnit()).asInstanceOf[CompilationUnit]
		var dec = ASTNodeSearchUtil.getFieldDeclarationNode(element, cu)
		return dec
	}

}