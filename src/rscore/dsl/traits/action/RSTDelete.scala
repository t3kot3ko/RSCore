package rscore.dsl.traits.action
import rscore.action.basic.DeleteProcessor
import rscore.dsl.entity.RSEntity

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