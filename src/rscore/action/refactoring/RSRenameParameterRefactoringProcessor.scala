package rscore.action.refactoring
import rscore.dsl.entity.RSParameter
import org.eclipse.jdt.internal.corext.refactoring.structure.ChangeSignatureProcessor
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import scala.collection.JavaConverters._
import rscore.helper.RefactoringHelper

class RSRenameParameterRefactoringProcessor(p: RSParameter, newName: String) extends RSAbstractRefactoringProcessor {
	override def createAction(): RSRefactoringAction = {
		val action: (() => Unit) =
			() => {
				val originalName = p.name
				val declaringMethod = p.parent
				
				val processor = new ChangeSignatureProcessor(declaringMethod.origin)
				val ref = new ProcessorBasedRefactoring(processor)
				
				val infos = processor.getParameterInfos().asScala
				val target = infos.filter(_.getOldName() == originalName)
				
				if(target.length != 0){
					target.first.setNewName(newName)
				}
				
				RefactoringHelper.performRefactoring(ref)
			}

		return new RSRefactoringAction(Seq(action))

	}

}