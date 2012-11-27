package rscore.tests.refactoring
import org.junit.Before
import org.junit.Test
import rscore.dsl.entity.RSWorkspace
import rscore.dsl.util.ImplicitConversions._
import rscore.dsl.entity.collection.By
import org.junit.After
import org.junit.Ignore

class RenameFieldTest extends RefactoringBaseTest {
	override val testGroupIdentifier = "rename_field"

	@Ignore
	def 変数を1つだけリネームする(): Unit = {
		prepareTest("RenameOneField")
	}
	
	
	/**
	 * プレフィクス new と，元の名前を大文字にした文字を結合して，新しい名前に変更する
	 * e.g. private int field -> private int newFIELD
	 */
	@Test
	def privateなフィールドの名前を変更する: Unit = {
		val testName = "RenamePrivateFields"
		prepareTest(testName)

		val $ = RSWorkspace
		$.project(projectName).pkg(testGroupIdentifier).classes.select(By.Name(testName)).first
			.fields.select(By.Modifier("private")).elements
			.foreach(field => field.rename("new" + field.name.toUpperCase()))

		doAssert(testName)
	}
	
	/*
	@After
	override def tearDown(): Unit = {
		println("teardown... ")
	}
	*/
	
}
