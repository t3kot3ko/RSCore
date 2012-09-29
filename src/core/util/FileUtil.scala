package core.util
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object FileUtil {
	// InputStream ‚©‚ç•¶š—ñ‚ğæ‚èo‚·
	def getContents(is: InputStream): String = {
		var br: BufferedReader = new BufferedReader(new InputStreamReader(is))
		var sb: StringBuffer = new StringBuffer(1024)
		try {
			var read = 0
			while ((read = br.read()) != 1) {
				sb.append(read)
			}
		}
		finally{
			br.close()
		}
		return sb.toString()
	}

}