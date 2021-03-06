package rscore.dsl.util
import org.eclipse.jdt.core.dom.ASTParser
import org.eclipse.jdt.core.dom.AST
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.jdt.core.dom.ASTNode

object ASTUtil {
	/**
	 * ICompilationUnit から CompilationUnit を生成する
	 */
	def createAST(cu: ICompilationUnit, kind: Int = ASTParser.K_COMPILATION_UNIT): ASTNode = {
		var parser = ASTParser.newParser(AST.JLS3)
		parser.setSource(cu)
		parser.setKind(kind)
		parser.setResolveBindings(true)
		parser.setBindingsRecovery(true)
		parser.setStatementsRecovery(true)
		
		var ast = parser.createAST(new NullProgressMonitor)
		return ast
	}
}