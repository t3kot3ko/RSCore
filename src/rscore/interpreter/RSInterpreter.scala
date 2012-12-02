package rscore.interpreter
import org.jruby.embed.ScriptingContainer
import org.eclipse.jdt.internal.ui.refactoring.contentassist.VariableNamesProcessor
import org.jruby.javasupport.JavaEmbedUtils
import org.jruby.embed.LocalVariableBehavior
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.OutputStream
import java.io.File
import java.io.FileOutputStream
import rscore.dsl.exception.IllegalScriptException

/**
 * Interpreter
 * This class is the wrapper for ScriptingContainer
 */
object RSInterpreter {
	var container: ScriptingContainer = _

	/**
	 * Initialize the container with specified option
	 * Note: With LocalVariableBehavior.PERSISTENT, variable life-cycle persists while the container object lives.
	 */
	def initContainer(
		option: LocalVariableBehavior = LocalVariableBehavior.PERSISTENT,
		withInitScript: Boolean = true): Unit = {

		this.container = new ScriptingContainer(option)

		if (withInitScript) {
			val initScript = ScriptHelper.generateInitScript()
			this.execScript(initScript)
		}

	}

	def terminateContainer(): Unit = {
		this.container.terminate()
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
	def getVariable[T](variableName: String): Option[T] = {
		try {
			var result = container.runScriptlet(variableName)
			return Some(result.asInstanceOf[T])
		} catch {
			case _ => return None
		}
	}

	/**
	 * Set any value to specified variable (named 'variableName')
	 *  A wrapper of container.put(String, Any)
	 */
	def setVariable(variableName: String, value: Any): Unit = {
		container.put(variableName, value)
	}

	/**
	 * Execute a script
	 * NOTE: Integers (values assumed integer) must be casted Long (otherwise, occur exception)
	 */
	def execScript[T](script: String): T = {
		try {
			val unit = container.parse(script)
			val ret = unit.run()
			val result = JavaEmbedUtils.rubyToJava(ret)
			return result.asInstanceOf[T]
		} catch {
			case _ => {
				throw new IllegalScriptException
			}
		}
	}

	def execScript(lines: Array[String]): Unit = {
		execScript(lines.mkString("\n"))
	}

}