package rscore.dsl.entity
import org.eclipse.jdt.core.IJavaElement
import rscore.dsl.common.RSObject
import rscore.dsl.traits.action.RSTDelete

abstract class RSEntity extends RSObject{
	val kind: RSEntity.Value 
	val self = this
	
	// 各型の識別子を入れておく（あとで，isInstanceOf[] しなくて済むように）
	// TODO: remove
	val __identifier: String
	
	// 実体を返す
	def origin(): IJavaElement
}

object RSEntity extends Enumeration{
	val CLASS, FIELD, MEMBER, METHOD, PACKAGE, PARAMETER, PROJECT, TYPE, WORKSPACE = Value
}
