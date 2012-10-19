package dsl.entity
import org.eclipse.jdt.core.IJavaElement

trait RSEntity[T] {
	// 各型の識別子を入れておく（あとで，isInstanceOf[] しなくて済むように）
	val __identifier: String
	
	
	val element: T
	// 実体を返す
	def origin(): T = return element
	
	// def toTarget(): RSTarget
	
}
