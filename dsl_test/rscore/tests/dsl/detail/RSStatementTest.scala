package rscore.tests.dsl.detail
import rscore.tests.dsl.DSLBaseTest
import rscore.dsl.entity.RSWorkspace
import org.junit.Test
import org.junit.Assert._
import org.junit.Before
import rscore.dsl.entity.RSMethod
import org.eclipse.jdt.core.dom.ASTNode


class RSStatementTest extends DSLBaseTest{
	override val testGroupIdentifier = "detail_test"
		
	@Before
	override def setUp(): Unit = {
		super.setUp()
	}
	
		
	@Test
	def ステートメント数を正しく取得できる(): Unit = {
		val testName = "StatementsCount"
		prepareTest(testName)
		
		val method = RSWorkspace.project(this.projectName).pkg(testGroupIdentifier).classes.first.first_method
		val body = method.body()
		assertEquals(5, body.statements().length)
		
		val expressionStatements = body.statements().filterByKind(ASTNode.EXPRESSION_STATEMENT)
		val vdStatements = body.statements().filterByKind(ASTNode.VARIABLE_DECLARATION_STATEMENT)
		
		assertEquals(1, expressionStatements.length)
		assertEquals(2, vdStatements.length)
		
	}
	
	def 自分の所属するエンティティを辿れる(): Unit = {
		val method = RSWorkspace.project(this.projectName).pkg(testGroupIdentifier).classes.first.first_method
		val body = method.body
		
		val p1 = body.parent
		val p2 = body.statements.first
	}
	

}