package rscore.dsl.detail.statement
import org.eclipse.jdt.core.dom.ExpressionStatement
import org.eclipse.jdt.core.dom.Expression
import rscore.dsl.common.RSObject
import rscore.dsl.common.RSNullObject
import rscore.dsl.detail.expression.RSExpression

class RSExpressionStatement(statement: ExpressionStatement, parent: RSObject = RSNullObject) extends RSStatement(statement){
	def expression(): RSExpression = {
		return new RSExpression(statement.getExpression())
	}

}