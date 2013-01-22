package rscore.tests.application
import rscore.tests.refactoring.RefactoringBaseTest
import rscore.dsl.entity.RSProject
import rscore.dsl.entity.collection.By
import org.junit.Test
import rscore.dsl.entity.collection.With
import rscore.dsl.entity.RSClass
import rscore.dsl.entity.RSField

class ApplicationTest extends RefactoringBaseTest {
	override val testGroupIdentifier = "application"

	// Extract below functions to helper
	protected def getCurrentProject(): RSProject = {
		return $.project(this.projectName)
	}
	protected def getFirstClass(): RSClass = {
		return this.getCurrentProject().pkg(this.testGroupIdentifier).classes().first
	}
}