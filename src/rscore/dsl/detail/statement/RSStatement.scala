package rscore.dsl.detail.statement
import org.eclipse.jdt.core.dom.Statement
import rscore.dsl.detail.RSDetailEntity
import rscore.dsl.common.RSObject
import rscore.dsl.common.RSNullObject
import org.eclipse.jdt.core.dom.ASTNode

/**
 * Statement:
 * - AssertStatement,
 * - Block,
 * - BreakStatement,
 * - ConstructorInvocation,
 * - ContinueStatement,
 * - DoStatement,
 * - EmptyStatement,
 * - ExpressionStatement,
 * - ForStatement,
 * - IfStatement,
 * - LabeledStatement,
 * - ReturnStatement,
 * - SuperConstructorInvocation,
 * - SwitchCase,
 * - SwitchStatement,
 * - SynchronizedStatement,
 * - ThrowStatement,
 * - TryStatement,
 * - TypeDeclarationStatement,
 * - VariableDeclarationStatement,
 * - WhileStatement
 */
class RSStatement(statement: Statement, parent: RSObject = RSNullObject) extends RSDetailEntity(statement, parent) {
	
	def isKindOf(kind: Int): Boolean = {
		if(RSStatement.Kinds.indexOf(kind) == -1){
			return false
		}
		
		return this.statement.getNodeType() == kind
	}
 
}

object RSStatement{
	val Kinds = Array[Int](
		ASTNode.ASSERT_STATEMENT,
		ASTNode.BLOCK,
		ASTNode.BREAK_STATEMENT,
		ASTNode.CONSTRUCTOR_INVOCATION,
		ASTNode.CONTINUE_STATEMENT,
		ASTNode.DO_STATEMENT,
		ASTNode.EMPTY_STATEMENT,
		ASTNode.EXPRESSION_STATEMENT,
		ASTNode.FOR_STATEMENT,
		ASTNode.IF_STATEMENT,
		ASTNode.LABELED_STATEMENT,
		ASTNode.RETURN_STATEMENT,
		ASTNode.SUPER_CONSTRUCTOR_INVOCATION,
		ASTNode.SWITCH_CASE,
		ASTNode.SWITCH_STATEMENT,
		ASTNode.SYNCHRONIZED_STATEMENT,
		ASTNode.THROW_STATEMENT,
		ASTNode.TRY_STATEMENT,
		ASTNode.TYPE_DECLARATION_STATEMENT,
		ASTNode.VARIABLE_DECLARATION_STATEMENT,
		ASTNode.WHILE_STATEMENT
	)
}