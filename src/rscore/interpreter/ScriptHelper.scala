package rscore.interpreter
import rscore.util.StringHelper

/**
 * スクリプトの調整，変形に関するヘルパ
 */
object ScriptHelper{
	// equals to project name
	private val ToplevelNamespace = "rscore"
	/**
	 * Packages to be imported first (before interpreting user scripts)
	 */
	private val DefaultImportPackages = Array[String](
			"dsl.entity.RSWorkspace",
			"dsl.entity.collection.By",
			"dsl.entity.collection.With",
			"dsl.entity.collection.WithOr",
			"dsl.entity.collection.WithAnd"
			)
			
	/**
	 * Generate 'java_import' statements
	 */
	def buildImportDeclaration(importPackages: Array[String] = DefaultImportPackages): String = {
		return importPackages.map(e => "java_import '" + ToplevelNamespace + "." + e + "'").mkString("\n")
	}
	
	def prepareScript(original: String): String = {
		val replaced = StringHelper.replaceAllChain(original, Constants.ReplacePair);
		return replaced;
	}
	
	/**
	 * Generate script for initialization the interpreter
	 */
	def generateInitScript(importPackages: Array[String] = DefaultImportPackages): String = {
		val initScript = "require 'java'\n" + buildImportDeclaration() + "\n"  + buildImportDeclaration(importPackages)
		return initScript
	}
}