package dsl.entity.collection
import dsl.entity.RSClass

class RSClasses(classes: Array[RSClass]) {
	

}
object RSClasses{
	implicit def convertToRSClasses(classes: Array[RSClass]) = new RSClasses(classes)
}