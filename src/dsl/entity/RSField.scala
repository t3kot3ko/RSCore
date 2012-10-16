package dsl.entity
import org.eclipse.jdt.core.IField
import dsl.util.ASTUtil
import org.eclipse.jdt.core.dom.MethodDeclaration
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
import org.eclipse.jdt.core.dom.Modifier
import scala.collection.JavaConversions._
import org.eclipse.jdt.core.dom.VariableDeclarationFragment
import org.eclipse.jdt.core.dom.FieldDeclaration
import dsl.search_trait.ModifierBasedSearchable
import dsl.search_trait.NameBasedSearchable

class RSField(val element: IField)
	extends RSEntity[IField]
	with ModifierBasedSearchable
	with NameBasedSearchable {
	
	// TODO: ワンライナだと長いから．
	val name: String = {
		() =>
			{
				var dec = getDeclaration()
				dec.fragments().first.asInstanceOf[VariableDeclarationFragment].getName().toString()
			}
	}.apply()
	
	def origin: IField = value

	// 自分自身の定義をASTから探す
	override def getDeclaration(): FieldDeclaration = {
		var cu = ASTUtil.createAST(element.getCompilationUnit()).asInstanceOf[CompilationUnit]
		var dec = ASTNodeSearchUtil.getFieldDeclarationNode(element, cu)
		return dec
	}
}

object RSField {
	implicit def convertToRSField(field: IField) = new RSField(field)
}