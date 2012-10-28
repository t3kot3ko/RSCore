package dsl.entity.collection

class Qualifier {}
/**
 * 検索用パラメータ
 * 検索の方法（And, Or, Not）を限定する，という意味で Qualifier（限定子）と呼ぶ
 */
case class With[T](values: T*){
	
}
object With{
	def or[T](values: T*): WithOr[T] = new WithOr[T](values: _*)
	def and[T](values: T*): WithAnd[T] = new WithAnd[T](values: _*)
	def out[T](values: T*): Without[T] = new Without[T](values: _*)
}

class WithOr[T](val values: T*) extends Qualifier {}
class WithAnd[T](val values: T*) extends Qualifier {}
class Without[T](val values: T*) extends Qualifier {}

