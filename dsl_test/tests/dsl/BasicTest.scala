package tests.dsl
import org.junit.Test
import org.junit.Assert._
import org.junit.Before
import dsl.entity.RSWorkspace

/**
 * ワークスペースから各エンティティを取得する，など基本動作に関するテスト
 */
class BasicTest extends DSLBaseTest{
	@Before
	override def setUp(): Unit = {
		super.setUp()
		prepareTest("BasicTest")
	}
		
	@Test
	def Workspaceを取得できる(): Unit = {
		assertNotNull($)
	}
	
	@Test
	def RSPackageを取得できる(): Unit = {
		var pkg = $.project(this.projectName).pkg("find_test")
		assertNotNull(pkg)
	}

}