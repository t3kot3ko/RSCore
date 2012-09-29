package core
import org.eclipse.core.runtime.Plugin
import org.eclipse.core.resources.IWorkspace
import java.io.File
import org.eclipse.core.runtime.IPath
import java.net.URL
import org.eclipse.core.runtime.Platform
import java.io.InputStream
import org.eclipse.core.runtime.Path
import org.eclipse.core.resources.ResourcesPlugin

object MyPlugin extends Plugin(){
	private val TEST_FILE_PATH: String = "testdata"
		
	def getDefault(): Plugin = {
		return MyPlugin
	}
	
	// 開いているランタイムワークベンチのワークスペース
	def getWorkspace() : IWorkspace = {
		return ResourcesPlugin.getWorkspace()
	}
	
	def getFileInPlugin(path: IPath): File = {
		var installURL: URL = new URL(getDefault().getBundle().getEntry("/"), path.toString())
		var localURL = Platform.asLocalURL(installURL)
		return new File(localURL.getFile())
	}
	
	def getInputStream(filename: String): InputStream = {
		var path = new Path(TEST_FILE_PATH).append(filename)
		var url: URL = new URL(getDefault().getBundle().getEntry("/"), path.toString())
		return url.openStream()
	}
	
}