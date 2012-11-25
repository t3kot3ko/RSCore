package dsl.entity
import org.eclipse.jdt.core.IType
import org.eclipse.jdt.core.dom.ASTNode
import dsl.util.ASTUtil
import org.eclipse.jdt.core.dom.CompilationUnit
import scala.collection.JavaConverters._
import org.eclipse.jdt.core.dom.TypeDeclaration
import org.eclipse.jdt.core.dom.FieldDeclaration
import org.eclipse.jdt.core.dom.VariableDeclaration
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
import org.eclipse.jdt.core.IMethod
import org.eclipse.jdt.core.dom.Modifier
import dsl.traits.search.NameBasedSearchable
import dsl.traits.search.ModifierBasedSearchable
import dsl.traits.search.CallbackBasedSearchable
import dsl.common.RSParam
import dsl.util.ImplicitConversions._
import dsl.entity.collection.With
import dsl.entity.collection.By
import dsl.traits.action.RSTIntroduceFactory
import dsl.entity.collection.RSCollection

class RSClass(val element: IType)
	extends RSEntity
	with NameBasedSearchable
	with ModifierBasedSearchable
	with CallbackBasedSearchable[RSClass]
	
	// Refactoring traits
	with RSTIntroduceFactory{

	val __identifier: String = "class"
	override val self = this

	val name: String = this.element.getElementName()
	override def origin: IType = element
	// override def toTarget(): RSTarget = new RSTarget(Array(element))

	def methods(): RSCollection[RSMethod] = {
		val rsMethods = element.getMethods().map(e => new RSMethod(e))
		return new RSCollection[RSMethod](rsMethods)
	}

	def isInterface(): Boolean = return this.element.isInterface()
	def isClass(): Boolean = return this.element.isClass()
	def kind: String = if (this.isClass) "class" else "interface"
	val superclassName = this.element.getSuperclassName()

	// Get method whose signature is matched
	def method(name: String, signature: Array[String]): RSMethod = {
		return new RSMethod(element.getMethod(name, signature))
	}
	
	/**
	 * クラスのコンストラクタを検索します
	 * （メソッド名がクラス名と一致しているものをコンストラクタとみなしています）
	 */
	def constructors(): RSCollection[RSMethod] = {
		return this.methods.select(By.Name(With.or(Array(this.name))))
	}

	// Get instance / class fields
	def fields(): RSCollection[RSField] = {
		val fs = this.element.getFields().map(e => new RSField(e))
		return new RSCollection[RSField](fs)
	}

	override def getDeclaration(): TypeDeclaration = {
		var cu = ASTUtil.createAST(element.getCompilationUnit()).asInstanceOf[CompilationUnit]
		var dec = ASTNodeSearchUtil.getTypeDeclarationNode(element, cu)
		return dec
	}

}
