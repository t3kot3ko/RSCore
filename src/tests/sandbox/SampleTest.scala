package tests.sandbox
import org.junit.Test
import org.junit.Assert._
import org.eclipse.core.resources.ResourcesPlugin
import org.junit.Ignore

class SampleTest {
	@Test
	def sample : Unit = {
		assertTrue(true)
	}
	
	@Test
	def GetWorkbench : Unit = {
		var ws = ResourcesPlugin.getWorkspace()
		assertNotNull(ws)
	}

}