package rscore.util

/**
 * 文字列置換に関するヘルパ
 */
object StringHelper {
	/**
	 * 与えられたペア（置換元，置換先文字列のペア）に基いて，それぞれ String#replaceAll(from, to) を行う
	 */
	def replaceAllChain(string: String, pairs : Array[(String, String)]): String = {
		def replace(string: String, pair: (String, String)): String = {
			val from = pair._1
			val to = pair._2
			
			string.replaceAll(from, to)
		}
		val result = pairs.foldLeft(string)(replace)
		return result
	}

}