package dsl.util
import org.eclipse.jdt.core.IMethod
import dsl.entity.RSMethod
import dsl.entity.collection.RSMethods
import dsl.entity.RSField
import org.eclipse.jdt.core.IField
import dsl.entity.collection.RSFields
import org.eclipse.jdt.core.IType
import dsl.entity.RSClass
import dsl.entity.collection.RSClasses

/**
 * ImplicitConversion するための変換メソッド群
 * TODO: 各コンパニオンオブジェクトに書いた implicit def ... を消す
 */
object ImplicitConversions {
	implicit def convertToRSMethod(method: IMethod) = new RSMethod(method)
	implicit def convertToRSMethods(methods: Array[RSMethod]) = new RSMethods(methods)
	
	implicit def converToRSField(field: IField) = new RSField(field)
	implicit def converToRSFields(fields: Array[RSField]) = new RSFields(fields)
	
	implicit def convertToRSClass(cls: IType) = new RSClass(cls)
	implicit def convertToRSClasses(clss: Array[RSClass]) = new RSClasses(clss)
}