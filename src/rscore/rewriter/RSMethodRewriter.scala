package rscore.rewriter
import org.eclipse.jface.text.Document
import rscore.dsl.entity.RSMethod
import rscore.dsl.util.ASTUtil
import org.eclipse.jdt.core.dom.MethodDeclaration
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil

class RSMethodRewriter(m: RSMethod) {
	private val origin = m.origin
	private val icu = this.origin.getCompilationUnit()
	private val document = new Document(this.icu.getSource())
	private val cu = ASTUtil.createCompilationUnit(true, icu) 
	private val dec: MethodDeclaration = ASTNodeSearchUtil.getMethodDeclarationNode(this.origin, cu)

	def changeModifier(newModifier: Int): Unit = {
		dec.setModifiers(newModifier)
	}

	// TODO: extract to AbstractRewriter
	def apply(newModifier: Int): Unit = {
		this.dec.setModifiers(newModifier)
		val edits = cu.rewrite(this.document, icu.getJavaProject().getOptions(true))
		edits.apply(this.document)
		val newSource = this.document.get()
		icu.getBuffer().setContents(newSource)
	}

}