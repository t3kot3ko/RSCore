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

class RSField(field: IField) extends ModifierBasedSearchable{
		
	/*
	def isPrivate(): Boolean = {
		var dec = getDeclaration()
		var modifiers = dec.getModifiers()
		return Modifier.isPrivate(modifiers)
	}
	*/

	def name(): String = {
		var dec = getDeclaration()
		return dec.fragments().first.asInstanceOf[VariableDeclarationFragment].getName().toString()
	}

	// é©ï™é©êgÇÃíËã`ÇASTÇ©ÇÁíTÇ∑
	override def getDeclaration(): FieldDeclaration = {
		var cu = ASTUtil.createAST(field.getCompilationUnit()).asInstanceOf[CompilationUnit]
		var dec = ASTNodeSearchUtil.getFieldDeclarationNode(field, cu)
		return dec
	}
}

object RSField {
	implicit def convertToRSField(field: IField) = new RSField(field)
}