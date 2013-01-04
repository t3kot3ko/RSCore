package rscore.rewriter
import org.eclipse.jface.text.Document
import rscore.dsl.util.ASTUtil
import rscore.dsl.entity.RSEntity
import rscore.dsl.entity.RSMember
import org.eclipse.jdt.core.dom.BodyDeclaration

class AbstractRewriter(element: RSMember) {
	protected val origin = element.origin
	protected val icu = this.origin.getCompilationUnit()
	protected val document = new Document(this.icu.getSource())
	protected val cu = ASTUtil.createCompilationUnit(true, icu) 
	protected val dec: BodyDeclaration = null
	
	// TODO: extract to AbstractRewriter
	def apply(): Unit = {
		val edits = cu.rewrite(this.document, this.icu.getJavaProject().getOptions(true))
		edits.apply(this.document)
		val newSource = this.document.get()
		this.icu.getBuffer().setContents(newSource)
	}
}