package core.action.refactoring

case class RSRefactoringAction(val changes: Seq[() => Unit]) {
	/**
	 * 指定されたリファクタリングアクションを順次実行する
	 * LTK をすべてラップするため
	 */
	def perform() : Boolean = {
		for(change <- changes){
			change.apply()
		}
		return true
	}

}