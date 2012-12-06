package rscore.dsl.detail.expression
import org.eclipse.jdt.core.dom.Expression
import rscore.dsl.detail.RSDetailElement
import org.eclipse.jdt.core.dom.Assignment

class RSExpression(expression: Expression) extends RSDetailElement(expression){
	def isAssignment(): Boolean = {
		return expression.isInstanceOf[Assignment]
	}
}