package dsl.query
import org.eclipse.jdt.core.dom.Modifier

/**
 * 使うかどうかわからない
 */
object RSModifier {
	def str2Modifier(modifierString: String): Modifier = {
		modifierString match {
			case "all" => return null
			case "private" => return null
			case "public" => return null
			case "static" => return null
			case "abstract" => return null
		}
	}

}
