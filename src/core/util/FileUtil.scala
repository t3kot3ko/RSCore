package core.util
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.FileInputStream

object FileUtil {
	/**
	 * ファイルパスをキーに，その内容を取得する
	 * @param eliminateBlankLines 空行を削除するか
	 */
	def getFileContents(filepath: String, eliminateBlankLines: Boolean = true): String = {
		val blankLine = """^\s*$"""

		if (eliminateBlankLines) {
			return scala.io.Source.fromFile(filepath).getLines.toArray
				.filterNot(line => line.matches(blankLine) || line == "")
				.mkString("\n")
		} else {
			return scala.io.Source.fromFile(filepath).getLines.toArray
				.mkString("\n")
		}
	}
	
	/**
	 * 文字列から空行を取り除く
	 */
	def eliminateBlankLines(string: String): String = {
		val blankLine = """^\s*$"""
		val lines = string.split("\n").filterNot(line => line.matches(blankLine) || line == "")
		return lines.mkString("\n")
	}

}