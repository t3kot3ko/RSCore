package dsl.entity.collection
import org.eclipse.jdt.core.IField
import org.eclipse.jdt.core.dom.Modifier
import dsl.entity.RSField

class RSFields(fields: Array[RSField]) {
	def where(modifier: String): Array[RSField] = {
		modifier match{
			case "private" => fields.filter(e => e.isPrivate())
		}
		
	}

}

object RSFields{
	implicit def convertToRSField(fields: Array[RSField]) = new RSFields(fields)
}