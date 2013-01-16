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

/**
 * TODO: rename (conflict the name 'PullUpRefactoringProcessor')
 */
class RSPullUpRefactoringProcessor(rsObject: RSObject, targetClass: RSClass) extends RSAbstractRefactoringProcessor {
	override def createAction(): RSRefactoringAction = {
		rsObject match {
			case f: RSField => return createActionForFieldCollection(Array(f.origin))
			case m: RSMethod => return createActionForMethodCollection(Array(m.origin))
			case c: RSCollection[RSMember] => {
				c.first match {
					case f: RSField => {
						val fs = c.origin.map(_.origin.asInstanceOf[IField])
						return createActionForFieldCollection(fs)
					}
					case m: RSMethod => {
						val ms = c.origin.map(_.origin.asInstanceOf[IMethod])
						return createActionForMethodCollection(ms)
					}
					case _ => return new RSRefactoringAction(Seq())
				}
			}
			case _ => return new RSRefactoringAction(Seq())
		}
	}

	private def createActionForFieldCollection(fs: Array[IField]): RSRefactoringAction = {
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
		return new RSRefactoringAction(Seq(action))
	}

	private def createActionForMethodCollection(ms: Array[IMethod]): RSRefactoringAction = {
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
		return new RSRefactoringAction(Seq(action))
	}

}