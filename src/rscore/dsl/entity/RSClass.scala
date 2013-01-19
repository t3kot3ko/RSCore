package rscore.dsl.entity
import org.eclipse.jdt.core.IType
import org.eclipse.jdt.core.dom.ASTNode
import rscore.dsl.util.ASTUtil
import org.eclipse.jdt.core.dom.CompilationUnit
import scala.collection.JavaConverters._
import org.eclipse.jdt.core.dom.TypeDeclaration
import org.eclipse.jdt.core.dom.FieldDeclaration
import org.eclipse.jdt.core.dom.VariableDeclaration
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
import org.eclipse.jdt.core.IMethod
import org.eclipse.jdt.core.dom.Modifier
import rscore.dsl.traits.search.NameBasedSearchable
import rscore.dsl.traits.search.ModifierBasedSearchable
import rscore.dsl.traits.search.CallbackBasedSearchable
import rscore.dsl.util.ImplicitConversions._
import rscore.dsl.entity.collection.With
import rscore.dsl.entity.collection.By
import rscore.dsl.traits.action.RSTIntroduceFactory
import rscore.dsl.entity.collection.RSCollection

class RSClass(val element: IType, parent: RSPackage)
	extends RSEntity
	with NameBasedSearchable
	with ModifierBasedSearchable
	with CallbackBasedSearchable[RSClass]

	// Refactoring traits
	with RSTIntroduceFactory {

	val __identifier: String = "class"
	override val kind = RSEntity.CLASS
	override val self = this

	val name: String = this.element.getElementName()
	override def origin: IType = element

	/**
	 * 内部クラスを返す
	 */
	def innerclasses(): RSCollection[RSClass] = {
		val innerClasses = element.getTypes().map(new RSClass(_, parent))
		return new RSCollection[RSClass](innerClasses)
	}

	/**
	 * 内部クラスを持つか否か
	 */
	def hasInnerclass(): Boolean = {
		return element.getTypes().length > 0
	}
	def has_innerclass(): Boolean = hasInnerclass() // just an alias

	def methods(): RSCollection[RSMethod] = {
		val rsMethods = element.getMethods().map(e => new RSMethod(e))
		return new RSCollection[RSMethod](rsMethods)
	}

	def firstMethod(): RSMethod = this.methods.first
	def first_method: RSMethod = this.firstMethod()	// just an alias

	def isInterface(): Boolean = return this.element.isInterface()
	def isClass(): Boolean = return this.element.isClass()
	
	/**
	 * スーパークラスに関する単純な解析
	 * おそらく，hasSuperclass くらいしか使わ（え）ない
	 */
	def hasSuperclass(): Boolean = (this.element.getSuperclassName() != null)
	def superclassName(): String = this.element.getSuperclassName()
	def superclassTypeSignature: String = this.element.getSuperclassTypeSignature()
	
	/**
	 * スーパークラスを（存在すれば）取得する
	 * USAGE: null チェックが必要なので，hasSuperclass() をしてから呼び出すのがよい
	 */
	def superclass(): RSClass = {
		if(!this.hasSuperclass()){
			return null
		}
		val superclass = this.getDeclaration().getSuperclassType()
		val binding = superclass.resolveBinding()
		if(binding == null){
			return null
		}
		
		val packageName = binding.getPackage().getName()
		println("pname = " + packageName)
		val className = binding.getName()
		println("cname = " + className)
		
		val projectName = this.parent.parent.name
		println("project = " + projectName)
		
		val cs =RSWorkspace.project(projectName).pkg(packageName).classes(true)
		println(cs.count)
		for(i <- 0 until cs.length){
			println(cs(i).name)
		}
		val result = RSWorkspace.project(projectName).pkg(packageName).classes().select(By.Name(className))
		if(result.count == 0){
			return null
		}
		
		return result.first
	}
	
		

	// Get method whose signature is matched
	def method(name: String, signature: Array[String]): RSMethod = {
		return new RSMethod(element.getMethod(name, signature))
	}
	def method(name: String): RSCollection[RSMethod] = {
		return this.methods.select(By.Name(name))
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
	
	def members(): RSCollection[RSMember] = {
		val fs = this.fields().map(_.asInstanceOf[RSMember]).toList
		val ms = this.methods().map(_.asInstanceOf[RSMember]).toList
		return new RSCollection[RSMember]((fs ::: ms).toArray)
	}

	override def getDeclaration(): TypeDeclaration = {
		var cu = ASTUtil.createAST(element.getCompilationUnit()).asInstanceOf[CompilationUnit]
		var dec = ASTNodeSearchUtil.getTypeDeclarationNode(element, cu)
		return dec
	}

}
