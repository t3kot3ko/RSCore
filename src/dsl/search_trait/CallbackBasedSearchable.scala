package dsl.search_trait
import org.eclipse.jdt.core.IJavaElement
import scala.reflect.This

trait CallbackBasedSearchable[T]{
	def passCallback(callback: T => Boolean): Boolean = {
		return true
	}

}