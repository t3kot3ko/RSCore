package rscore.action.refactoring
import rscore.dsl.entity.RSMember
import rscore.dsl.entity.RSClass
import org.eclipse.jdt.internal.ui.preferences.JavaPreferencesSettings
import org.eclipse.jdt.core.IMember
import rscore.dsl.entity.RSField
import rscore.dsl.entity.collection.RSCollection
import rscore.dsl.common.RSObject
import rscore.dsl.entity.RSMethod
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import rscore.helper.RefactoringHelper
import org.eclipse.jdt.core.IMethod
import org.eclipse.jdt.core.IField

class RSPullUpRefactoringProcessor(rsObject: RSObject, targetClass: RSClass) extends RSAbstractRefactoringProcessor {
	private val empty: (() => Unit) = () => {}

	override def createAction(): RSRefactoringAction = {
		val actionSeq: Seq[(() => Unit)] = rsObject match {
			case f: RSField => {
				Seq(createActionForFieldCollection(Array(f.origin)))
			}
			case m: RSMethod => Seq(createActionForMethodCollection(Array(m.origin)))
			case c: RSCollection[RSMember] => {
				val fs = c.filter(_.isInstanceOf[RSField]).map(_.origin.asInstanceOf[IField]).toArray
				val ms = c.filter(_.isInstanceOf[RSMethod]).map(_.origin.asInstanceOf[IMethod]).toArray
				val fsAction = if (fs.length > 0) createActionForFieldCollection(fs) else empty
				val msAction = if (ms.length > 0) createActionForMethodCollection(ms) else empty
				Seq(fsAction, msAction)
			}
			case _ => Seq()
		}
		return new RSRefactoringAction(actionSeq)
	}

	/**
	 * TODO: もうすこしきれいな場合分けを考える
	 */
	private def createActionForFieldCollection(fs: Array[IField]): (() => Unit) = {
		val action: (() => Unit) =
			() => {
				val project = fs.first.getJavaProject()
				val processor = new org.eclipse.jdt.internal.corext.refactoring.structure.PullUpRefactoringProcessor(
					fs.map(_.asInstanceOf[IMember]),
					JavaPreferencesSettings.getCodeGenerationSettings(project))

				processor.setDestinationType(targetClass.origin())
				processor.setReplace(true)
				new ProcessorBasedRefactoring(processor)

				val ref = processor.getRefactoring()
				RefactoringHelper.performRefactoring(ref)
			}
		return action
	}

	private def createActionForMethodCollection(ms: Array[IMethod]): (() => Unit) = {
		val action: (() => Unit) = {
			() =>
				{
					val project = ms.first.getJavaProject()
					val processor = new org.eclipse.jdt.internal.corext.refactoring.structure.PullUpRefactoringProcessor(
						ms.map(_.asInstanceOf[IMember]),
						JavaPreferencesSettings.getCodeGenerationSettings(project))

					processor.setDestinationType(targetClass.origin())

					// 引き上げたら元のメソッドを削除するオプション（なぜかメソッドに対しては必要）
					processor.setDeletedMethods(ms)

					new ProcessorBasedRefactoring(processor)
					val ref = processor.getRefactoring()
					RefactoringHelper.performRefactoring(ref)
				}
		}
		return action
	}

}