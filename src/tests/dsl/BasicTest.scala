package tests.dsl
import org.junit.Test
import org.junit.Assert._

/**
 * ワークスペースから各エンティティを取得する，など基本動作に関するテスト
 */
class BasicTest extends BaseTest{
	@Test
	def Workspaceを取得できる(): Unit = {
		assertNotNull($)
	}
	
	@Test
	def RSPackageを取得できる(): Unit = {
		var pkg = project.pkg("test.dsl")
		assertNotNull(pkg)
	}

}