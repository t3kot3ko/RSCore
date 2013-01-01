package rscore.dsl.entity
import org.eclipse.jdt.core.IMember
import rscore.dsl.traits.action.RSTDelete
import rscore.dsl.traits.action.RSTPullUp
import rscore.dsl.traits.action.RSTMoveMember
import rscore.dsl.traits.search.ModifierBasedSearchable

abstract class RSMember(val element: IMember)
	extends RSEntity
	with ModifierBasedSearchable
	
	with RSTDelete 
	with RSTPullUp
	with RSTMoveMember{

	override val __identifier = "member"
	override val kind = RSEntity.MEMBER

	def origin = element
	override val self = this
}