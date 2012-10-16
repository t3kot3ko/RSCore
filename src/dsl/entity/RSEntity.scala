package dsl.entity
import org.eclipse.jdt.core.IJavaElement
import dsl.action.RSTarget

class RSEntity(val element: IJavaElement) {
	def toTarget(): RSTarget = {
		return new RSTarget(Array(element))
	}
}

