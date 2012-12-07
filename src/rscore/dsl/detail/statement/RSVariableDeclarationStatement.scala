package rscore.dsl.detail.statement
import org.eclipse.jdt.core.dom.VariableDeclarationStatement
import rscore.dsl.common.RSNullObject
import rscore.dsl.common.RSObject

class RSVariableDeclarationStatement(
	statement: VariableDeclarationStatement,
	parent: RSObject = RSNullObject)
	extends RSStatement(statement, parent) {
	
	
	def aaa: Unit = {
	}
	

}