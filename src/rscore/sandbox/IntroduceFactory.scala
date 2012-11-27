package rscore.sandbox
import org.eclipse.jdt.core.ICompilationUnit
import rscore.helper.SelectionHelper
import org.eclipse.jdt.internal.corext.refactoring.code.IntroduceFactoryRefactoring
import rscore.helper.RefactoringHelper
import rscore.dsl.entity.RSClass
import org.eclipse.jdt.core.ISourceRange
import rscore.dsl.entity.RSMethod
import rscore.dsl.entity.collection.RSCollection

object IntroduceFactory {
	def introduceFactorySample(unit: ICompilationUnit) = {
		var source: String = unit.getSource()
		var range = SelectionHelper.getSelection(source)

		println("offset = " + range(0))
		println("length = " + range(1))

		var refactoring: IntroduceFactoryRefactoring = new IntroduceFactoryRefactoring(unit, range(0), range(1))
		RefactoringHelper.performRefactoring(refactoring)
	}

	def introduceFactorySample(cls: RSClass): Unit = {
		val cu: ICompilationUnit = cls.origin().getCompilationUnit()
		val constructors: RSCollection[RSMethod] = cls.constructors
		// val firstConstructor: RSMethod = constructors.first

		for (c <- constructors.elements) {
			val nameRange: ISourceRange = c.origin.getNameRange()
			val offset = nameRange.getOffset()
			val length = nameRange.getLength()
			println("offset = " + offset)
			println("length= " + length)
			var refactoring: IntroduceFactoryRefactoring = new IntroduceFactoryRefactoring(cu, offset, length)
			RefactoringHelper.performRefactoring(refactoring)

		}

	}
}