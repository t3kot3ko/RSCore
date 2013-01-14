package rscore.tests.refactoring
import org.junit.Test
import org.eclipse.jdt.internal.corext.refactoring.structure.PushDownRefactoringProcessor
import org.eclipse.jdt.internal.corext.refactoring.RefactoringAvailabilityTester
import rscore.dsl.entity.RSWorkspace
import rscore.dsl.entity.collection.By
import org.eclipse.jdt.core.IMember
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import rscore.helper.RefactoringHelper
import org.junit.Ignore
import rscore.dsl.entity.collection.RSCollection
import rscore.dsl.entity.RSMember

class PushDownTest extends RefactoringBaseTest {
	override val testGroupIdentifier = "push_down"

	@Test
	def クラスメンバすべてを一気に同一ファイル内のサブクラスにPushDownできる(): Unit = {
		val testName = "InnerSimple"
		prepareTest(testName)

		val cls = RSWorkspace.project(this.projectName).pkg(testGroupIdentifier).classes().Select(By Name testName).first
		val members: RSCollection[RSMember] = cls.members()
		members.pushDown()
		doAssert(testName, true)
	}
	
	@Test
	def クラスメンバすべてをひとつずつに同一ファイル内のサブクラスにPushDownできる(): Unit = {
		val testName = "InnerSimple"
		prepareTest(testName)

		val cls = RSWorkspace.project(this.projectName).pkg(testGroupIdentifier).classes().Select(By Name testName).first
		val fields = cls.fields()
		val methods = cls.methods()
		
		fields.push_down()
		methods.push_down()
		doAssert(testName, true)
	}

	// at least, following options are required
	@Ignore
	def sandbox: Unit = {
		val testName = "InnerSimple"
		prepareTest(testName)

		val cls = RSWorkspace.project(this.projectName).pkg(testGroupIdentifier).classes().Select(By Name testName).first
		val members: Array[IMember] = cls.members().toArray.map(_.origin)

		RefactoringAvailabilityTester.isPushDownAvailable(members)
		val processor = new PushDownRefactoringProcessor(members)
		val refactoring = new ProcessorBasedRefactoring(processor)

		RefactoringHelper.performRefactoring(refactoring)

		doAssert(testName, true)

	}

}