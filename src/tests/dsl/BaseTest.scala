package tests.dsl
import dsl.entity.RSWorkspace
import dsl.entity.RSProject

/**
 * すべてのテストの基底テスト
 * 
 * NOTICE: 
 * ワークスペースと設定ファイルは，通常起動と同じものを使用するので，クリアしない！
 */
class BaseTest {
	val $ = RSWorkspace
	var project: RSProject = null  
}