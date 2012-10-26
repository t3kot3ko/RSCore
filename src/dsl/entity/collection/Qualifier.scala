package dsl.entity.collection

class Qualifier{}
/**
 * 検索用パラメータ
 * 検索の方法（And, Or, Not）を限定する，という意味で Qualifier（限定子）と呼ぶ
 */
case class With[T] (val values: T*) extends Qualifier{
	// MEMO: ケースクラスの補助コンストラクタは new ... でしか呼べない
	def this(values: Array[T]) = this(values:_*)
}
case class WithAnd[T] (val values: T*) extends Qualifier{}

case class Without[T](val values: T*) extends Qualifier{
	def this(value: Array[T]) = this(value:_*)
}

