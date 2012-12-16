package rscore.dsl.detail.expression
import org.eclipse.jdt.core.dom.Expression
import rscore.dsl.detail.RSDetailEntity
import org.eclipse.jdt.core.dom.Assignment
import rscore.dsl.common.RSObject
import rscore.dsl.common.RSNullObject
import org.eclipse.jdt.core.dom.ASTNode

/**
 * Expression:
 * - Annotation,
 * - ArrayAccess,
 * - ArrayCreation,
 * - ArrayInitializer,
 * - Assignment,
 * - BooleanLiteral,
 * - CastExpression,
 * - CharacterLiteral,
 * - ClassInstanceCreation,
 * - ConditionalExpression,
 * - FieldAccess,
 * - InfixExpression,
 * - InstanceofExpression,
 * - MethodInvocation,
 * - Name,
 * - NullLiteral,
 * - NumberLiteral,
 * - ParenthesizedExpression,
 * - PostfixExpression,
 * - PrefixExpression,
 * - StringLiteral,
 * - SuperFieldAccess,
 * - SuperMethodInvocation,
 * - ThisExpression,
 * - TypeLiteral,
 * - VariableDeclarationExpression
 */

class RSExpression(expression: Expression, parent: RSObject = RSNullObject)
	extends RSDetailEntity(expression, parent) {

	/**
	 * Expression Çï\Ç∑íËêî
	 */
	private val ExpressionTypes = Array[Int](
		ASTNode.ANNOTATION_TYPE_DECLARATION,
		ASTNode.ARRAY_ACCESS,
		ASTNode.ARRAY_CREATION,
		ASTNode.ARRAY_INITIALIZER,
		ASTNode.ASSIGNMENT,
		ASTNode.BOOLEAN_LITERAL,
		ASTNode.CAST_EXPRESSION,
		ASTNode.CHARACTER_LITERAL,
		ASTNode.CLASS_INSTANCE_CREATION,
		ASTNode.CONDITIONAL_EXPRESSION,
		ASTNode.FIELD_ACCESS,
		ASTNode.INFIX_EXPRESSION,
		ASTNode.INSTANCEOF_EXPRESSION,
		ASTNode.METHOD_INVOCATION,
		ASTNode.NULL_LITERAL,
		ASTNode.NUMBER_LITERAL,
		ASTNode.PARENTHESIZED_EXPRESSION,
		ASTNode.POSTFIX_EXPRESSION,
		ASTNode.PREFIX_EXPRESSION,
		ASTNode.STRING_LITERAL,
		ASTNode.SUPER_FIELD_ACCESS,
		ASTNode.SUPER_METHOD_INVOCATION,
		ASTNode.THIS_EXPRESSION,
		ASTNode.TYPE_LITERAL,
		ASTNode.VARIABLE_DECLARATION_EXPRESSION)

	def isAssignment(): Boolean = {
		return expression.isInstanceOf[Assignment]
	}
	
	def hasKind(kind: Int): Boolean = {
		return expression.getNodeType() == kind
	}

}