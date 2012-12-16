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
import rscore.dsl.detail.statement.RSStatement
import rscore.dsl.detail.statement.RSStatementCollection

class RSBody(block: Block, val parent: RSObject = RSNullObject) extends RSDetailEntity(block, parent){
	def statements(kind: Int): RSStatementCollection = {
		return this.statements().filterByKind(kind)
	}
	
	def statements(): RSStatementCollection = {
		val ss = block.statements().asScala.map(_.asInstanceOf[Statement]).map(new RSStatement(_, parent)).toList
		return new RSStatementCollection(ss)
	}
}