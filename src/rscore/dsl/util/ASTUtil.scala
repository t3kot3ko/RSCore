package rscore.dsl.util
import org.eclipse.jdt.core.dom.ASTParser
import org.eclipse.jdt.core.dom.AST
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.CompilationUnit

object ASTUtil {
	/**
	 * ICompilationUnit Ç©ÇÁ CompilationUnit Çê∂ê¨Ç∑ÇÈ
	 */
	def createAST(cu: ICompilationUnit, kind: Int = ASTParser.K_COMPILATION_UNIT, level: Int = AST.JLS3): ASTNode = {
		// When an illegal level option is specified, exception will be thrown
		if(Array(AST.JLS2, AST.JLS3, AST.JLS4).indexOf(level) == -1){
			throw new IllegalArgumentException
		}
		
		var parser = ASTParser.newParser(level)
		parser.setSource(cu)
		parser.setKind(kind)
		parser.setResolveBindings(true)
		parser.setBindingsRecovery(true)
		parser.setStatementsRecovery(true)
		
		var ast = parser.createAST(new NullProgressMonitor)
		return ast
	}
	
	def createASTWithJLS2(cu: ICompilationUnit, kind: Int = ASTParser.K_COMPILATION_UNIT): ASTNode = {
		return createAST(cu, kind, AST.JLS2)
	}
	
	/**
	 * ICompilationUnit Ç©ÇÁèëÇ´çûÇ›ÅièëÇ´ä∑Ç¶Åjâ¬î\Ç» AST Çê∂ê¨Ç∑ÇÈ
	 * NOTE: AST is rewritable only with JSL2 level
	 */
	def createRewritableCompilationUnit(cu: ICompilationUnit, kind: Int = ASTParser.K_COMPILATION_UNIT): CompilationUnit = {
		val unit = createASTWithJLS2(cu, kind).asInstanceOf[CompilationUnit]
		unit.recordModifications()	// enable to rewrite
		return unit
	}
	
	def createCompilationUnit(rewritable: Boolean, cu: ICompilationUnit, kind: Int = ASTParser.K_COMPILATION_UNIT): CompilationUnit = {
		if(rewritable){
			return createRewritableCompilationUnit(cu, kind)
		}
		else{
			return createAST(cu, kind).asInstanceOf[CompilationUnit]
		}
		
	
	}
}