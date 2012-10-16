package dsl.action

/**
 * エンティティに対するアクションを表すクラス
 * Action has a processor モデルを採用しておく
 */
class RSAction(val processor: RSActionProcessor) {
	var params = Set[RSActionParam[_]]()
	def perform(): RSActionStatus = {
		var action = processor.createAction()
		return null
	}
	
	// Memo: Fluent interface
	def addParam(param: RSActionParam[_]): RSAction = {
		this.params += param
		return this
	}
}