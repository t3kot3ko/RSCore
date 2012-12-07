package rscore.dsl.detail
import org.eclipse.jdt.core.dom.ASTNode
import rscore.dsl.common.RSObject
import rscore.dsl.common.RSNullObject

class RSDetailEntity(node: ASTNode, parent: RSObject = RSNullObject) {
	def startPos(): Int = node.getStartPosition()
	def length(): Int = node.getLength()
	def origin: ASTNode = node
}