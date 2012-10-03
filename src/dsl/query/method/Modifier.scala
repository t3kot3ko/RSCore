package dsl.query
import org.eclipse.jdt.core.dom.Modifier

object Modifier extends Enumeration {
	type Modifier = Value
	val ALL, PRIVATE, PUBLIC, PROTECTED, STATIC = Value
}