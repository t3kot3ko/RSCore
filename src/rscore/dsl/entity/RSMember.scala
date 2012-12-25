package rscore.dsl.entity
import org.eclipse.jdt.core.IMember
import rscore.dsl.traits.action.RSTDelete
import rscore.dsl.traits.action.RSTPullUp

class RSMember(val element: IMember)
	extends RSEntity
	with RSTDelete 
	with RSTPullUp{

	override val __identifier = "member"
	override val kind = RSEntity.MEMBER

	def origin = element
	override val self = this
}