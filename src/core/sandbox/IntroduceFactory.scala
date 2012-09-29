package core.sandbox
import org.eclipse.jdt.core.ICompilationUnit
import core.helper.SelectionHelper
import org.eclipse.jdt.internal.corext.refactoring.code.IntroduceFactoryRefactoring
import core.helper.RefactoringHelper

object IntroduceFactory {
	def introduceFactorySample(unit: ICompilationUnit) = {
		var source: String = unit.getSource()
		var range = SelectionHelper.getSelection(source)

		println("offset = " + range(0))
		println("length = " + range(1))

		var refactoring: IntroduceFactoryRefactoring = new IntroduceFactoryRefactoring(unit, range(0), range(1))
		RefactoringHelper.performRefactoring(refactoring)
	}
}