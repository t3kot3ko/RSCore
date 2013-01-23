package rscore.tests.refactoring
import org.junit.Test
import org.junit.Assert._
import rscore.dsl.entity.collection.By
import org.eclipse.jdt.internal.corext.refactoring.structure.ChangeSignatureProcessor
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import rscore.helper.RefactoringHelper

class ChangeSignatureTest extends RefactoringBaseTest {
	override val testGroupIdentifier = "change_signature"

	@Test
	def ï‘ãpílå^ÇïœçXÇ∑ÇÈ: Unit = {
		val testName = "ChangeReturnTypeTest"
		prepareTest(testName)
		
		val method = $.project(this.projectName).pkg(this.testGroupIdentifier).classes().first.methods().selectOne(By name "m")
		method.change_return_type("Foo")
		
		doAssert(testName)
	}
	@Test
	def ï‘ãpílå^ÇïœçXÇ∑ÇÈ_sandbox(): Unit = {
		val testName = "ChangeReturnTypeTest"
		prepareTest(testName)
		
		val method = $.project(this.projectName).pkg(this.testGroupIdentifier).classes().first.methods().selectOne(By name "m")
		val processor = new ChangeSignatureProcessor(method.origin)
		
		processor.setNewReturnTypeName("Foo")
		
		val refactoring = new ProcessorBasedRefactoring(processor)
		RefactoringHelper.performRefactoring(refactoring)
		
		doAssert(testName)
	}

}