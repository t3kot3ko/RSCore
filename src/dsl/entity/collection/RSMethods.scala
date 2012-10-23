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

class RSMethods(val elements: Array[RSMethod])
	extends RSCollection[RSMethod] with Find[RSMethod] {

	// ‚·‚×‚Ä‚Ì—v‘f‚ð•Ô‚·
	override def all(): Array[RSMethod] = elements

	// ŽÀ‘Ì‚ª—~‚µ‚­‚È‚Á‚½Žži‚¢‚¸‚êÁ‚·j
	override def origin(): Array[IMethod] = elements.map(e => e.origin)

	/**
	 * $.methods.where(modifier -> Array("public", "private"), xxx -> Array(1, 2, 3)...)
	 * where ‚É—^‚¦‚ç‚ê‚½ƒNƒGƒŠ‚Í‚·‚×‚Ä AND ‚Å‰ðŽß‚³‚ê‚é
	 */

	override def select(query: RSQuery): Array[RSMethod] = {
		query match {
			case NameQuery(names: Array[String]) => this.elements.filter(e => e.hasNamesOr(names))
			case NameRegQuery(nameregs: Array[String]) => this.elements.filter(e => e.hasRegexeMathcedNamesOr(nameregs))
			case ModifierQuery(modifiers: Array[String]) => return this.elements.filter(e => e.hasModifiersOr(modifiers))
			case _ => return this.elements
		}
	}

	override def exclude(query: RSQuery): Array[RSMethod] = {
		query match {
			case NameQuery(names: Array[String]) => this.elements.filterNot(e => e.hasNamesOr(names))
			case NameRegQuery(nameregs: Array[String]) => this.elements.filterNot(e => e.hasRegexeMathcedNamesOr(nameregs))
			case ModifierQuery(modifiers: Array[String]) => return this.elements.filterNot(e => e.hasModifiersOr(modifiers))
			case _ => return this.elements
		}
	}

	def first = if (elements.length > 1) elements(0) else null
}
