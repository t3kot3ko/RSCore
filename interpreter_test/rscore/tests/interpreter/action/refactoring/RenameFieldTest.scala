package rscore.tests.interpreter.action.refactoring
import rscore.tests.interpreter.InterpreterRefactoringBaseTest
import org.junit.Test
import rscore.dsl.entity.RSWorkspace
import org.junit.Before

class RenameFieldTest extends InterpreterRefactoringBaseTest{
	override val testGroupIdentifier = "rename_field"
		
	@Test
	def privateなフィールドの名前を変更する: Unit = {
		val testName = "RenamePrivateFields"
		prepareTest(testName)

		val script = """
		project.pkg("%s").classes.select(By.Name("%s")).first
			.fields.Select(By.Modifier("private")).each do |field|
			   	field.rename("new" + field.name.upcase)
			end
		""".format(this.testGroupIdentifier, testName)
		interpreter.execScript(script)

		doAssert(testName)
	}

}