package core.action.refactoring
import dsl.entity.RSClass
import org.eclipse.jdt.core.ICompilationUnit
import dsl.entity.RSMethod
import org.eclipse.jdt.core.ISourceRange
import org.eclipse.jdt.internal.corext.refactoring.code.IntroduceFactoryRefactoring
import core.helper.RefactoringHelper
import dsl.common.RSObject

class IntroduceFactoryRefactoringProcessor(rsObject: RSObject) extends AbstractRefactoringProcessor {
	override def createAction(): RSRefactoringAction = {
		rsObject match{
			case c: RSClass => return createActionForClass(c)
			case m: RSMethod => return createActionForConstructorMethod(m)
			case _ => return new RSRefactoringAction(Seq())
		}
	}

	private def createElement(method: RSMethod): (() => Unit) = {
		val action: () => Unit =
			() => {
				val cu: ICompilationUnit = method.origin().getCompilationUnit()
				val nameRange: ISourceRange = method.origin.getNameRange()
				val offset = nameRange.getOffset()
				val length = nameRange.getLength()
				println("offset = " + offset)
				println("length= " + length)
				var refactoring: IntroduceFactoryRefactoring = new IntroduceFactoryRefactoring(cu, offset, length)
				RefactoringHelper.performRefactoring(refactoring)
			}

		return action
	}
	private def createActionForConstructorMethod(constructor: RSMethod): RSRefactoringAction = {
		return new RSRefactoringAction(Seq(createElement(constructor)))
	}

	private def createActionForClass(cls: RSClass): RSRefactoringAction = {
		return new RSRefactoringAction(cls.constructors.map(createElement(_)).toSeq)
	}

}