package rscore.dsl.entity.collection
import dsl.entity.RSClass
import dsl.util.ImplicitConversions._
import dsl.common.RSParam
import scala.util.matching.Regex
import org.eclipse.jdt.core.IType
import dsl.query.RSQuery
import dsl.query.NameQuery
import dsl.query.NameRegQuery
import dsl.query.ModifierQuery
import dsl.query.CallbackQuery
import dsl.entity.RSEntity

class RSClasses(val elements: Array[RSClass])
	extends RSCollection[RSClass]{

	override def origin: Array[IType] = elements.map(e => e.origin)
	override def all: Array[RSClass] = elements
	
	def select_with_foo(): String = {
		return "dummy"
	}

}