package rscore.dsl.entity
import org.eclipse.jdt.core.IMember
import rscore.dsl.traits.action.RSTDelete

class RSMember(val element: IMember)
	extends RSEntity
	with RSTDelete {
	
	
	override val __identifier = "member"

	def origin = element
	override val self = this
}