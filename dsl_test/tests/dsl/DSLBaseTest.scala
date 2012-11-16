package tests.dsl
import tests.common.BaseTest
import dsl.entity.RSWorkspace

class DSLBaseTest extends BaseTest{
	override val testGroupIdentifier = "find_test"
	protected val $ = RSWorkspace
}