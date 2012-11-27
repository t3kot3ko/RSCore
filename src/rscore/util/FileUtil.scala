package rscore.util
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.FileInputStream

object FileUtil {
	/**
	 * ファイルパスをキーに，その内容を取得する
	 * @param eliminateBlankLines 空行を削除するか
	 */
	def getFileContents(filepath: String,
		eliminateBlankLines: Boolean = true,
		eliminateCommentLines: Boolean = true): String = {
		
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
	 * 複数行文字列（\n 区切り文字列）から空行を取り除く
	 * @param eliminateHeadBlanks 加えて，先頭の空白をすべて削除する
	 */
	def eliminateBlankLines(string: String, eliminate: Boolean = false): String = {
		val blankLine = """^\s*$"""
		val lines = string.split("\n").filterNot(line => line.matches(blankLine) || line == "")
			.map(e => if (eliminate) eliminateHeadBlanks(e) else e)
		return lines.mkString("\n")
	}

	/**
	 * 文字列の先頭の空白を取り除く
	 */
	def eliminateHeadBlanks(string: String): String = {
		val headBlank = """^\s+"""
		val r = string.replaceAll(headBlank, "")
		return r
	}

	/**
	 * コメント行 (// ...) を取り除く
	 */
	def eliminateCommentLines(string: String): String = {
		val commentLine = """^\s*//.*$"""
		val lines = string.split("\n").filterNot(line => line.matches(commentLine))
		return lines.mkString("\n")
	}

}