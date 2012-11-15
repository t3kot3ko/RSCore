package tests.eval.test.collection;

import org.junit.Test
import org.jruby.embed.ScriptingContainer
import org.junit.Ignore

class EvalTest {
	@Test
	def インタープリタを呼び出せる(): Unit = {
		val container: ScriptingContainer = new ScriptingContainer();
		val source = """
			require 'java'
			java_import 'dsl.entity.RSWorkspace'
			java_import 'dsl.entity.collection.By'
			java_import 'dsl.entity.collection.With'
			java_import 'dsl.entity.collection.WithOr'
			java_import 'dsl.entity.collection.WithAnd'
			
			a = RSWorkspace.project("Sample").pkg("test.dsl").classes.first.fields.selects(By.Modifier("public"))
			
			# f.rename("")
		"""

		val res = container.runScriptlet(source);

		println(res.toString());
	}

}