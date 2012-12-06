package rscore.tests.sandbox;
import rscore.tests.dsl.DSLBaseTest
import org.junit.Before
import org.junit.Assert._
import rscore.dsl.entity.collection.RSCollection
import rscore.dsl.entity.RSMethod
import org.junit.Test
import scala.collection.JavaConversions._
import org.eclipse.jdt.core.dom.VariableDeclarationStatement
import org.eclipse.jdt.core.dom.VariableDeclarationFragment
import org.eclipse.jdt.core.dom.Block
import org.eclipse.jdt.core.dom.ExpressionStatement
import scala.collection.mutable.Buffer
import org.eclipse.jdt.core.dom.Assignment
import org.eclipse.jdt.internal.corext.refactoring.code.ExtractTempRefactoring
import rscore.helper.RefactoringHelper
import rscore.tests.refactoring.RefactoringBaseTest
import rscore.tests.sandbox.SandboxBaseTest
import org.eclipse.jdt.core.dom.ASTVisitor
import org.junit.Ignore

/**
 * TODO: extracted and renamed to 'sandbox test'
 */
class MyVisitor extends ASTVisitor {
	override def visit(statement: ExpressionStatement): Boolean = {
		val expression = statement.getExpression()
		if(expression.isInstanceOf[Assignment]){
			val assign = expression.asInstanceOf[Assignment]
			println(assign.getLeftHandSide().toString() + assign.getOperator().toString() + assign.getRightHandSide().toString())
		}
		super.visit(statement)
	}
}

class Test01 extends SandboxBaseTest {
	@Test
	def sandbox2(): Unit = {
		val testName = "Test01"
		prepareTest(testName)

		val m = $.project(this.projectName).pkg("sandbox").classes.first.first_method
		m.getDeclaration().getBody().accept(new MyVisitor)
	}

	@Ignore
	def sandbox(): Unit = {
		val testName = "Test01"
		prepareTest(testName)

		val m = $.project(this.projectName).pkg("sandbox").classes.first.first_method
		val cu = m.origin.getCompilationUnit()

		val d = m.getDeclaration()
		val body = d.getBody()
		val variableDeclarationStatements =
			body.statements().filter(_.isInstanceOf[VariableDeclarationStatement])
				.map(e => e.asInstanceOf[VariableDeclarationStatement])

		println(variableDeclarationStatements.length)

		val vds = variableDeclarationStatements.first
		val start = vds.getStartPosition()
		val length = vds.getLength()

		// ‚±‚±‚Å“¾‚ç‚ê‚é‚Ì‚ÍŽ©•ª‚ªŠ‘®‚·‚é CompilationUnit ‚Ìæ“ª‚©‚ç‚ÌˆÊ’u
		println("start: " + start)
		println("length: " + length)

		val source = m.origin.getCompilationUnit().getSource()
		println(source.substring(start, start + length))

		val expressionStatements = getExpressionStatements(body)
		val es = expressionStatements.first

		val left = es.getExpression().asInstanceOf[Assignment].getLeftHandSide()
		val esStart = left.getStartPosition()
		val esLength = left.getLength()
		println("start " + esStart)
		println("length " + esLength)

		assertEquals("i", source.substring(esStart, esStart + esLength))

		val right = es.getExpression().asInstanceOf[Assignment].getRightHandSide();
		val s = right.getStartPosition()
		val l = right.getLength()

		val subStr = source.substring(s, s + l)
		println(subStr)

		val ref = new ExtractTempRefactoring(cu, s, l)
		ref.setTempName("extracted")
		RefactoringHelper.performRefactoring(ref)

		val newSource = $.project(this.projectName).pkg("sandbox").classes.first.origin.getCompilationUnit().getSource()
		println(newSource)

		doAssert(testName)
	}

	def getExpressionStatements(block: Block): Buffer[ExpressionStatement] = {
		val result = block.statements().filter(_.isInstanceOf[ExpressionStatement])
			.map(e => e.asInstanceOf[ExpressionStatement])
		return result
	}

}