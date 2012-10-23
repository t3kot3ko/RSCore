package dsl.entity
import org.eclipse.jdt.core.IJavaElement

abstract class RSEntity{
	// 各型の識別子を入れておく（あとで，isInstanceOf[] しなくて済むように）
	val __identifier: String
	
	// 実体を返す
	def origin(): IJavaElement
}
