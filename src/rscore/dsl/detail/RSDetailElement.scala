package rscore.dsl.detail
import org.eclipse.jdt.core.dom.ASTNode

class RSDetailElement(node: ASTNode) {
	def startPos(): Int = node.getStartPosition()
	def length(): Int = node.getLength()
	
	def origin: ASTNode = node
}