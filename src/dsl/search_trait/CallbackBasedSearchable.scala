package dsl.search_trait
import org.eclipse.jdt.core.IJavaElement

trait CallbackBasedSearchable[T]{
	val element: IJavaElement 
	
	def passCallback(callback: IJavaElement => Boolean): Boolean = {
		return callback(element)
	}

}