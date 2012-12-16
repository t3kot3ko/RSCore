package rscore.dsl.entity
import org.eclipse.jdt.core.IMember
import rscore.dsl.traits.action.RSTDelete

class RSMember(val element: IMember)
	extends RSEntity
	with RSTDelete {

	override val __identifier = "member"
	override val kind = RSEntity.MEMBER

	def origin = element
	override val self = this
}