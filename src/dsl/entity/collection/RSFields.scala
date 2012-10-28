package dsl.entity.collection
import org.eclipse.jdt.core.IField
import org.eclipse.jdt.core.dom.Modifier
import dsl.entity.RSField
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import dsl.common.RSParam
import scala.util.matching.Regex
import dsl.query.RSQuery
import dsl.query.NameQuery
import dsl.query.ModifierQuery
import dsl.query.NameRegQuery
import dsl.traits.action.RSTSelfEncapsulateFieldRefactoring

class RSFields(val elements: Array[RSField])
	extends RSCollection[RSField]
	/*with RSTSelfEncapsulateFieldRefactoring */{

	// override val self = this

	override def origin: Array[IField] = elements.map(_.origin)
	// def toTarget(): Array[RSTarget] = this.elements.map(e => e.toTarget())

}
