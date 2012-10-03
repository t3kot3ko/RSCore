package dsl.entity
import org.eclipse.jdt.core.IType
import org.eclipse.jdt.core.dom.ASTNode
import dsl.util.ASTUtil
import org.eclipse.jdt.core.dom.CompilationUnit
import scala.collection.JavaConverters._
import org.eclipse.jdt.core.dom.TypeDeclaration
import dsl.entity.RSField

class RSClass(var name: String, typ: IType){
	def methods(methodType: SearchQuery = SearchQuery.ALL): Array[RSMethod] = {
		methodType match{
			case "static" => 
			case "private" =>
		}
		return this.typ.getMethods().map(e => RSMethod.create(e))
	}
	
	def fields() : Array[RSField] = {
		return this.typ.getFields().map(e => RSField.create(e))
	}
	
	
	def ast() : ASTNode = {
		var cu = this.typ.getCompilationUnit()
		var newCU = ASTUtil.createAST(cu).asInstanceOf[CompilationUnit]
		var types = newCU.types().asScala.map(e => e.asInstanceOf[TypeDeclaration])
		var target = types.find(e => e.getName().toString() == this.name).get
		assert(target != null)
		
		return target
	}

}