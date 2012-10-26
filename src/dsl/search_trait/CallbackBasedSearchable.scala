package dsl.search_trait
import org.eclipse.jdt.core.IJavaElement
import scala.reflect.This
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