package dsl.entity.collection
import org.eclipse.jdt.core.IField
import org.eclipse.jdt.core.dom.Modifier
import dsl.entity.RSField
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import dsl.common.RSParam
import scala.util.matching.Regex
import dsl.target.RSTarget
import dsl.query.RSQuery
import dsl.query.NameQuery
import dsl.query.ModifierQuery
import dsl.query.NameRegQuery
import scala.collection.mutable.ArraySeq

class RSFields(val elements: Array[RSField])
	extends RSCollection[RSField]{

	override def origin: Array[IField] = elements.map(_.origin)
	def toTarget(): Array[RSTarget] = this.elements.map(e => e.toTarget())

}
