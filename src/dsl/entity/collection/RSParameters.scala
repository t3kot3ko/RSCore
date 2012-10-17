package dsl.entity.collection
import dsl.entity.RSParameter
import org.eclipse.jdt.core.ILocalVariable
import dsl.common.RSParam

class RSParameters(val elements: Array[RSParameter]) extends RSCollection[RSParameter] with Where[RSParameter]{
	override def origin: Array[ILocalVariable] = elements.map(e => e.origin)
	
	override def where(params: Array[RSParam[_]]): Array[RSParameter] = {
		return executeWhereQuery(params).toArray[RSParameter]
	}
	override def dispatchWhere(param: RSParam[_]): Set[RSParameter] = {
		param.value match{
			case ("name", names: Array[String]) => return elements.filter(e => e.hasNamesOr(names)).toSet
			case ("signature", sigs: Array[String]) => return elements.filter(e => e.hasSignaturesOr(sigs)).toSet
			case (_: String, _: Array[_]) => throw new Exception("Invalid Key")
		}
	}

}