package rscore.tests.rewriter

import org.junit.Test
import rscore.tests.refactoring.RefactoringBaseTest
import rscore.dsl.entity.RSWorkspace
import org.junit.Before
import rscore.dsl.entity.RSProject
import rscore.dsl.entity.collection.By
import org.eclipse.jdt.core.dom.ASTParser
import org.eclipse.jdt.core.dom.AST
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.core.dom.MethodDeclaration
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
import org.eclipse.jdt.core.dom.Modifier
import org.eclipse.jface.text.Document
import org.junit.Ignore
import rscore.rewriter.RSMethodRewriter

class RSMethodRewriterTest extends RefactoringBaseTest{
	override val testGroupIdentifier = "rewriter"
	private var project: RSProject = _
		
	@Before
	override def setUp(): Unit = {
		super.setUp()
		this.project = RSWorkspace.project(this.projectName)
	}
	
	@Test
	def メソッドのアクセス修飾子を変更できる(): Unit = {
		val testName = "RewriteMethodModifier"
		prepareTest(testName)
		val cls = RSWorkspace.project(this.projectName).pkg(this.testGroupIdentifier).classes().first
		val m = cls.firstMethod()
		
		val r = new RSMethodRewriter(m)
		r.changeModifier(Modifier.PRIVATE)
		r.apply
		doAssert(testName)
	}
	
	@Test
	def メソッド名を変更できる(): Unit = {
		val testName = "RewriteMethodName"
		prepareTest(testName)
		val cls = RSWorkspace.project(this.projectName).pkg(this.testGroupIdentifier).classes().first
		val m = cls.firstMethod()
		
		val r = new RSMethodRewriter(m)
		r.changeName("b")
		r.apply
		doAssert(testName)
	}
	
	@Ignore
	def sandbox(): Unit = {
		val testName = "RewriteMethodModifier"
		prepareTest(testName)
		
		val cls = RSWorkspace.project(this.projectName).pkg(this.testGroupIdentifier).classes().first
		val method = cls.methods.first
		
		val origin = cls.methods.first.origin
		val icu = origin.getCompilationUnit()
		
		// convert core.ICompilationUnit to core.dom.CompilationUnit
		val parser = ASTParser.newParser(AST.JLS2)
		parser.setSource(icu)
		parser.setKind(ASTParser.K_COMPILATION_UNIT)
		parser.setResolveBindings(true)
		parser.setBindingsRecovery(true)
		parser.setStatementsRecovery(true)
		val ast = parser.createAST(new NullProgressMonitor)
		
		val cu = ast.asInstanceOf[CompilationUnit]
		cu.recordModifications()
		
		// val source = icu.getBuffer().getContents()
		val source = icu.getSource()
		val document = new Document(source)
		
		var dec: MethodDeclaration = ASTNodeSearchUtil.getMethodDeclarationNode(origin, cu)
		dec.setModifiers(Modifier.PRIVATE)
		
		val edits = cu.rewrite(document,icu.getJavaProject().getOptions(true))
		edits.apply(document)
		val newSource = document.get()
		
		println(newSource)
		icu.getBuffer().setContents(newSource)
		
		doAssert(testName)
		
	}

}