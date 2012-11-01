package tests.refactoring
import org.junit.Before
import org.junit.Test
import dsl.entity.RSWorkspace
import dsl.util.ImplicitConversions._
import dsl.entity.collection.By

class RenameFieldTest extends RefactoringBaseTest{
	override val testGroupIdentifier = "rename_field" 
	
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
		.fields.select(By.Modifier("private"))
		.foreach(field => field.rename("new" + field.name.toUpperCase()))
		
		doAssert(testName)
	}
	
}