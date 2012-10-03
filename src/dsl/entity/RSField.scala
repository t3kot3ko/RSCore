package dsl.entity
import org.eclipse.jdt.core.IField

class RSField extends IField{

}

object RSField{
	def create(field: IField): RSField = {
		return field.asInstanceOf[RSField]
	}
}