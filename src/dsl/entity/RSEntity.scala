package dsl.entity
import org.eclipse.jdt.core.IJavaElement

trait RSEntity[T] {
	val element: T
	
	// ŽÀ‘Ì‚ð•Ô‚·
	def origin(): T = {
		return element
	}
}