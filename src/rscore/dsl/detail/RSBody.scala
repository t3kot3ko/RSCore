package rscore.dsl.detail
import org.eclipse.jdt.core.dom.Block
import scala.collection.JavaConverters._
import org.eclipse.jdt.core.dom.ExpressionStatement
import org.eclipse.jdt.core.dom.Statement
import org.eclipse.jdt.core.dom.VariableDeclarationStatement
import org.eclipse.jdt.core.dom.ASTVisitor
import rscore.dsl.common.RSObject
import rscore.dsl.common.RSNullObject
import rscore.dsl.detail.statement.RSExpressionStatement
import rscore.dsl.detail.statement.RSVariableDeclarationStatement

class RSBody(block: Block, parent: RSObject = RSNullObject) extends RSDetailEntity(block, parent){
	def expressionStatements(): RSDetailEntityCollection[RSExpressionStatement] = {
		val ss = getStatements[ExpressionStatement]().map(new RSExpressionStatement(_, parent))
		return new RSDetailEntityCollection[RSExpressionStatement](ss)
	}
	
	def variableDeclarationStatements(): RSDetailEntityCollection[RSVariableDeclarationStatement] = {
		val ss = getStatements[VariableDeclarationStatement]().map(new RSVariableDeclarationStatement(_, parent))
		return new RSDetailEntityCollection[RSVariableDeclarationStatement](ss)
	}
	
	
	// block 以下の T (Expression, VariableDeclaration...) ステートメントを取得する
	private def getStatements[T <: Statement](): List[T] = {
		val ss = block.statements().asScala.map(_.asInstanceOf[T])
		ss.toList
	}
}