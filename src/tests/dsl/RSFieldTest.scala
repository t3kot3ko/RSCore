package tests.dsl
import org.eclipse.ui.IWorkbench
import org.junit.Test
import org.junit.Assert._
import dsl.entity.RSWorkspace
import org.junit.Before
import dsl.entity.RSWorkspace
import dsl.entity.RSProject
import scala.reflect.This
import dsl.entity.collection.By
import dsl.util.ImplicitConversions._


class FieldTest extends BaseTest{
	@Test
	def RSClass‚ðŽæ“¾‚Å‚«‚é(): Unit = {
		var cls = project.pkg("test.dsl").classes.first
		var publicIntField = cls.fields.select(By name("publicInt"))
		assert(publicIntField.length == 1)
	}
	
}