package dsl.traits.search
import org.eclipse.jdt.core.IJavaElement
import dsl.entity.RSEntity

trait CallbackBasedSearchable[T <: RSEntity]{
	val self: T
	def passCallback(callback: T => Boolean): Boolean = {
		return callback(self)
	}
	
	def passCallbacksOr(callbacks: Array[T => Boolean]): Boolean = {
		return callbacks.exists(_(self))
	}
	
	def passCallbacksAnd(callbacks: Array[T => Boolean]): Boolean = {
		return callbacks.forall(_(self))
	}
}