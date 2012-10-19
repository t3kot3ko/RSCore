package dsl.entity.collection
import dsl.entity.RSClass
import dsl.util.ImplicitConversions._
import dsl.common.RSParam
import scala.util.matching.Regex
import org.eclipse.jdt.core.IType

class RSClasses(val elements: Array[RSClass])
	extends RSCollection[RSClass]
	with Where[RSClass] {
	
	
	override def origin: Array[IType] = elements.map(e => e.origin)
	override def all: Array[RSClass] = elements
	
	/**
	 * $.methods.where(modifier -> Array("public", "private"), xxx -> Array(1, 2, 3)...)
	 * where ‚É—^‚¦‚ç‚ê‚½ƒNƒGƒŠ‚Í‚·‚×‚Ä AND ‚Å‰ðŽß‚³‚ê‚é
	 */
	override def where(params: Array[RSParam[_]]): Array[RSClass] = {
		return executeWhereQuery(params).toArray[RSClass]
	}

	override def dispatchWhere(param: RSParam[_]): Set[RSClass] = {
		param.value match {
			case ("modifier", modifiers: Array[String]) =>
				return this.elements.filter(e => e.hasModifiersOr(modifiers)).toSet
			case ("name", names: Array[String]) =>
				return this.elements.filter(e => e.hasNamesOr(names)).toSet
			case ("namereg", names: Array[Regex]) =>
				return this.elements.filter(e => e.hasRegexeMathcedNamesOr(names)).toSet
			case ("namereg", names: Array[String]) =>
				return this.elements.filter(e => e.hasRegexeMathcedNamesOr(names)).toSet
				
			// EXPERIMENTAL
			case ("callback", callbacks: Array[RSClass=> Boolean]) =>
				return this.elements.filter(e => e.passCallbacksOr(callbacks)).toSet
				
			case _ => return this.elements.toSet
		}
	}

}