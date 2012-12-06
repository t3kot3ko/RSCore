package rscore.dsl.detail.statement
import org.eclipse.jdt.core.dom.ExpressionStatement
import org.eclipse.jdt.core.dom.Expression

class RSExpressionStatement(statement: ExpressionStatement) extends RSStatement(statement){
	def expression(): Expression = {
		return statement.getExpression()
	}

}