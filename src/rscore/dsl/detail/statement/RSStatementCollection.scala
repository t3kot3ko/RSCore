package rscore.dsl.detail.statement
import rscore.dsl.detail.RSDetailEntityCollection

class RSStatementCollection(statements: List[RSStatement]) extends RSDetailEntityCollection(statements){
	def filterByKind(kind: Int): RSStatementCollection = {
		val filtered = statements.filter(e => e.isKindOf(kind))
		return new RSStatementCollection(filtered)
	}

}