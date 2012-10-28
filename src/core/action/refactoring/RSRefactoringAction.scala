package core.action.refactoring
import org.eclipse.ltk.core.refactoring.Change
import org.eclipse.core.runtime.NullProgressMonitor

case class RSRefactoringAction(val changes: Seq[() => Unit]) {
	/**
	 * 指定されたリファクタリングアクションを順次実行する
	 * LTK をすべてラップするため
	 */
	def perform() : Unit = {
		for(change <- changes){
			change()
		}
	}

}