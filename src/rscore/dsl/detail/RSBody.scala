package rscore.dsl.detail
import org.eclipse.jdt.core.dom.Block
import scala.collection.JavaConverters._
import org.eclipse.jdt.core.dom.ExpressionStatement
import org.eclipse.jdt.core.dom.Statement
import org.eclipse.jdt.core.dom.VariableDeclarationStatement
import org.eclipse.jdt.core.dom.ASTVisitor

class RSBody(block: Block) {
	def expressionStatements(): List[ExpressionStatement] = {
		return getStatements[ExpressionStatement]()
	}
	
	def variableDeclarationStatements(): List[VariableDeclarationStatement] = {
		return getStatements[VariableDeclarationStatement]()
	}
	
	private def getStatements[T <: Statement](): List[T] = {
		class MyVisitor extends ASTVisitor{
			override def visit(statement: ExpressionStatement): Boolean = {
				super.visit(statement)
			}
			
		}
		
		val visitor = new MyVisitor
		block.accept(visitor)
		
		val ss = block.statements().asScala.map(_.asInstanceOf[T])
		return ss.toList
	}
	
	

}