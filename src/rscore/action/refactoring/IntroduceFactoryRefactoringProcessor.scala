package rscore.action.refactoring
import rscore.dsl.entity.RSClass
import org.eclipse.jdt.core.ICompilationUnit
import rscore.dsl.entity.RSMethod
import org.eclipse.jdt.core.ISourceRange
import org.eclipse.jdt.internal.corext.refactoring.code.IntroduceFactoryRefactoring
import rscore.helper.RefactoringHelper
import rscore.dsl.common.RSObject
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.jdt.core.IMember

class IntroduceFactoryRefactoringProcessor(rsObject: RSObject) extends AbstractRefactoringProcessor {
	override def createAction(): RSRefactoringAction = {
		rsObject match {
			case c: RSClass => return createActionForClass(c)
			case m: RSMethod => return createActionForConstructorMethod(m)
			case _ => return new RSRefactoringAction(Seq())
		}
	}

	private def createElement(method: RSMethod): (() => Unit) = {
		val action: () => Unit =
			() => {
				// println(method.origin().getParent().getClass().toString())
				val cu: ICompilationUnit = method.origin().getCompilationUnit()
				val nameRange: ISourceRange = method.origin.getNameRange()
				val offset = nameRange.getOffset()
				val length = nameRange.getLength()
				println("offset = " + offset)
				println("length= " + length)
				var refactoring: IntroduceFactoryRefactoring = new IntroduceFactoryRefactoring(cu, offset, length)

				println("initial = " + refactoring.checkInitialConditions(new NullProgressMonitor))
				println("final = " + refactoring.checkFinalConditions(new NullProgressMonitor))

				val status = RefactoringHelper.performRefactoring(refactoring)
				println(status)
			}

		return action
	}
	private def createActionForConstructorMethod(constructor: RSMethod): RSRefactoringAction = {
		return new RSRefactoringAction(Seq(createElement(constructor)))
	}

	private def createActionForClass(cls: RSClass): RSRefactoringAction = {
		println("createActionForClass")
		return new RSRefactoringAction(cls.constructors.elements.map(createElement(_)).toSeq)
	}

}