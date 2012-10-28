package dsl.entity.collection
import org.eclipse.jdt.core.IMethod
import scala.collection.JavaConversions._
import dsl.entity.RSMethod
import dsl.util.ASTUtil
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
import org.eclipse.jdt.core.dom.MethodDeclaration
import scala.collection.mutable.Buffer
import org.eclipse.jdt.core.dom.Modifier
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.LinkedList
import scala.util.matching.Regex
import dsl.common.RSParam
import dsl.query.RSQuery
import dsl.query.NameQuery
import dsl.query.NameRegQuery
import dsl.query.ModifierQuery
import dsl.query.CallbackQuery

class RSMethods(val elements: Array[RSMethod])
	extends RSCollection[RSMethod] {

	// ‚·‚×‚Ä‚Ì—v‘f‚ð•Ô‚·
	override def all(): Array[RSMethod] = elements

	override def origin(): Array[IMethod] = elements.map(e => e.origin)
}
