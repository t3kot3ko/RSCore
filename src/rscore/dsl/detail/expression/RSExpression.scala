package rscore.dsl.detail.expression
import org.eclipse.jdt.core.dom.Expression
import rscore.dsl.detail.RSDetailEntity
import org.eclipse.jdt.core.dom.Assignment
import rscore.dsl.common.RSObject
import rscore.dsl.common.RSNullObject

class RSExpression(expression: Expression, parent: RSObject = RSNullObject)
	extends RSDetailEntity(expression, parent) {
	
	def isAssignment(): Boolean = {
		return expression.isInstanceOf[Assignment]
	}
}