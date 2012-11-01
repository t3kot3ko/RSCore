package core.util
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.FileInputStream

object FileUtil {
	def getFileContents(filepath: String): String = {
		/*
		val sb = new StringBuffer(1024)
		for (line <- scala.io.Source.fromFile(filepath).getLines) {
			sb.append(line)
			sb.append("\n")
		}
		return sb.toString()
		*/
		return scala.io.Source.fromFile(filepath).getLines.toArray.mkString("\n")

	}
	// InputStream ‚©‚ç•¶Žš—ñ‚ðŽæ‚èo‚·
	@Deprecated
	def getContents(is: InputStream): String = {
		var br: BufferedReader = new BufferedReader(new InputStreamReader(is))
		var sb: StringBuffer = new StringBuffer(1024)
		// try {
		var read = 0
		while ((read = br.read()) != 1) {
			sb.append(read)
		}
		// }
		/*
		finally{
			br.close()
		}
		*/
		return sb.toString()
	}

	@Deprecated
	def getFileContents_deprecated(filepath: String): String = {
		return getContents(getFileInputStream(filepath))
	}

	def getFileInputStream(filePath: String): InputStream = {
		return new FileInputStream(filePath)
	}

}