package tests.dsl
import dsl.entity.RSWorkspace
import dsl.entity.RSProject
import org.junit.Before
import org.junit.Test
import org.junit.Assert._

/**
 * すべてのテストの基底テスト
 * 
 * NOTICE: 
 * ワークスペースと設定ファイルは，通常起動と同じものを使用するので，クリアしない！
 */
class BaseTest {
	val $ = RSWorkspace
	var project: RSProject = null  
	
	@Before
	def setUp(): Unit = {
		$.refresh()
		println("Workspace has been refreshed")
		
		this.project = $.project("Sample")
		println("Project has been set")
	}
	
}