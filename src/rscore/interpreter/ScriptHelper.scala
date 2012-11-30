package rscore.interpreter

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
	
	/**
	 * Generate script for initialization the interpreter
	 */
	def generateInitScript(importPackages: Array[String] = DefaultImportPackages): String = {
		val initScript = "require 'java'\n" + buildImportDeclaration() + "\n"  + buildImportDeclaration(importPackages)
		return initScript
	}
	
	def buildScript(script: String): String = {
		val newScript = "require 'java'\n" + buildImportDeclaration() + "\n" + script
		return newScript
	}
}