package dsl.traits.action
import core.action.basic.DeleteProcessor
import dsl.entity.RSEntity

trait RSTDelete extends RefactoringTrait{
	def delete(): Unit = {
		self match {
			case e: RSEntity =>{
				val processor = new DeleteProcessor(e)
				processor.createAction().perform()
			}
		}
		println("deleted")
	}

}