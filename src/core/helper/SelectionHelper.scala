package core.helper
import org.eclipse.jface.text.ITextSelection
import org.eclipse.jface.text.TextSelection

object SelectionHelper {
	private val SQUARE_BRACKET_OPEN = "/*[*/"
	private val SQUARE_BRACKET_OPEN_LENGTH = SQUARE_BRACKET_OPEN.length()
	private val SQUARE_BRACKET_CLOSE = "/*]*/"
	private val SQUARE_BRACKET_CLOSE_LENGTH = SQUARE_BRACKET_CLOSE.length()

	def getTextSelection(range: Array[Int]): ITextSelection ={
		assert(range.length == 2)
		return new TextSelection(range(0), range(1))
	}
	/**
	 * SQUARE_BRACKET_OPEN と，SQUARE_BRACKET_CLOSE で囲まれた部分の範囲を計算する
	 * （テストを書くときには便利かも？）
	 * @return start and length
	 */
	def getSelection(source: String, openDelimiter: String = SQUARE_BRACKET_OPEN, closeDelimier: String = SQUARE_BRACKET_CLOSE): Array[Int] = {
		var start = -1
		var end = -1
		
		var includingStart = source.indexOf(openDelimiter)
		var excludingStart = source.indexOf(closeDelimier)
		var includingEnd = source.lastIndexOf(openDelimiter)
		var excludingEnd = source.lastIndexOf(closeDelimier)
		
		var openLength = openDelimiter.length()
		var closeLength = closeDelimier.length()

		if (includingStart > excludingStart && excludingStart != -1) {
			includingStart = -1
		} else if (excludingStart > includingStart && includingStart != -1) {
			excludingStart = -1
		}

		if (includingEnd < excludingEnd) {
			includingEnd = -1
		} else if (excludingEnd < includingEnd) {
			excludingEnd = -1
		}

		if (includingStart != -1) {
			start = includingStart;
		} else {
			start = excludingStart + closeLength 
		}

		if (excludingEnd != -1) {
			end = excludingEnd
		} else {
			end = includingEnd + closeLength
		}
		assert(start >= 0 && end >= 0 && end >= start)

		var result: Array[Int] = Array[Int](start, end - start)
		println("|"+ source.substring(result(0), result(0) + result(1)) + "|");
		return result;

	}
}