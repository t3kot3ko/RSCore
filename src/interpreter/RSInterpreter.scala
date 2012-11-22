package interpreter
import org.jruby.embed.ScriptingContainer
import org.eclipse.jdt.internal.ui.refactoring.contentassist.VariableNamesProcessor
import org.jruby.javasupport.JavaEmbedUtils
import org.jruby.embed.LocalVariableBehavior

/**
 * Interpreter
 * This class is the wrapper for ScriptingContainer
 */
object RSInterpreter {
	var container: ScriptingContainer = _

	/**
	 * Initialize the container with specified option
	 * Note: LocalVariableBehavior.PERSISTENT
	 */
	def initContainer(option: LocalVariableBehavior = LocalVariableBehavior.PERSISTENT): Unit = {
		this.container = new ScriptingContainer(option)
	}

	/**
	 * Assign a value (named 'valueToAssign') to a variable (named 'variabeName')
	 */
	def assignVariable(variableName: String, valueToAssign: Any): Unit = {
		container.put(variableName, valueToAssign)
	}

	/**
	 * Returns a value assigned a variable (named as 'variableName')
	 */
	def getVariable[T](variableName: String): T = {
		return container.runScriptlet(variableName).asInstanceOf[T]
	}

	/**
	 * Execute a script
	 * NOTE: Integers (values assumed integer) must be casted Long (otherwise, occur exception)
	 */
	def execScript[T](script: String): T = {
		val unit = container.parse(script)
		val ret = unit.run()
		val result = JavaEmbedUtils.rubyToJava(ret)

		return result.asInstanceOf[T]

		/*
		val res = this.container.runScriptlet(script);
		println(res.toString());
		*/
	}

	def execScript(lines: Array[String]): Unit = {
		execScript(lines.mkString("\n"))
	}

}