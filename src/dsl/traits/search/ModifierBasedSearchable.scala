package dsl.traits.search
import org.eclipse.jdt.core.IJavaElement
import dsl.util.ASTUtil
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.core.dom.MethodDeclaration
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
import org.eclipse.jdt.core.dom.Modifier
import org.eclipse.jdt.core.dom.BodyDeclaration

// アクセス修飾子を持ったエンティティ
trait ModifierBasedSearchable {
	val declaration: BodyDeclaration = getDeclaration()
	val modifiersNum: Int = declaration.getModifiers()
	
	// must be overridden
	def getDeclaration() : BodyDeclaration
	
	def hasModifier(string: String): Boolean = {
		string match{
			case "public" => return this.isPublic()
			case "private" => return this.isPrivate()
			case "protected" => return this.isProtected()
			case "static" => return this.isStatic()
			case "final" => return this.isFinal()
			case "abstract" => return this.isAbstract()
			case _ => return false
		}
	}
	
	// modifires のうち，1つでも持ってるもの
	def hasModifiersOr(modifiers: Array[String]): Boolean = {
		return modifiers.exists(hasModifier(_))
	}
	// modifires をすべて持っているもの（public static など）
	def hasModifiersAnd(modifires: Array[String]) : Boolean = {
		return modifires.forall(hasModifier(_))
	}
	def isPrivate(): Boolean = Modifier.isPrivate(this.modifiersNum)
	def isPublic(): Boolean = Modifier.isPublic(this.modifiersNum)
	def isProtected(): Boolean = Modifier.isProtected(this.modifiersNum)
	def isStatic(): Boolean = Modifier.isStatic(this.modifiersNum)
	def isFinal(): Boolean = Modifier.isFinal(this.modifiersNum)
	def isAbstract(): Boolean = Modifier.isAbstract(this.modifiersNum)
	
}