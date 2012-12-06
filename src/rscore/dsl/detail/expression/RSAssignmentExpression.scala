package rscore.dsl.detail.expression
import org.eclipse.jdt.core.dom.Assignment

class RSAssignmentExpression(expression: Assignment) {
	def left(): RSExpression = {
		return new RSExpression(expression.getLeftHandSide())
	}
	
	def right(): RSExpression = {
		return new RSExpression(expression.getRightHandSide())
	}
	
	def operator(): Assignment.Operator = {
		val o = expression.getOperator()
		return o
	}

}