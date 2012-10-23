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
import dsl.target.RSTarget

class RSField(val element: IField)
	extends RSEntity
	with ModifierBasedSearchable
	with NameBasedSearchable {
	
	val __identifier = "field"
	
	// TODO: ワンライナだと長いから．
	val name: String = {
		() =>
			{
				var dec = getDeclaration()
				dec.fragments().first.asInstanceOf[VariableDeclarationFragment].getName().toString()
			}
	}.apply()
	
	override def origin: IField = element
	// override def toTarget: RSTarget = new RSTarget(Array(element))
	def toTarget(id: String, parameters: (String, String)): RSTarget = new RSTarget(id, this, parameters)
	def toTarget(): RSTarget = new RSTarget(this)

	// 自分自身の定義をASTから探す
	override def getDeclaration(): FieldDeclaration = {
		var cu = ASTUtil.createAST(element.getCompilationUnit()).asInstanceOf[CompilationUnit]
		var dec = ASTNodeSearchUtil.getFieldDeclarationNode(element, cu)
		return dec
	}
}