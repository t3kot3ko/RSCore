package rscore.action

class RSAbstractAction(changes: Seq[() => Unit]) {
	/**
	 * 指定されたリファクタリングアクションを順次実行する
	 * LTK をすべてラップするため
	 */
	def perform(): Boolean = {
		for (change <- changes) {
			change.apply()
		}
		return true
	}

}