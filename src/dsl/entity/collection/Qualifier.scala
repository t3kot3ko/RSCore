package dsl.entity.collection

class Qualifier {}
/**
 * 検索用パラメータ
 * 検索の方法（And, Or, Not）を限定する，という意味で Qualifier（限定子）と呼ぶ
 */
case class With[T](values: T*){
	
}
object With{
	def or[T](values: Array[T]): WithOr[T] = new WithOr[T](values)
	def or[T](values: String*): WithOr[String] = new WithOr[String](values.toArray)
	def or[T](value: String): WithOr[String] = new WithOr[String](Array(value))
	
	def and[T](values: Array[T]): WithAnd[T] = new WithAnd[T](values)
	def and[T](values: String*): WithAnd[String] = new WithAnd[String](values.toArray)
	def and[T](value: String): WithAnd[String] = new WithAnd[String](Array(value))
	
	def out[T](values: Array[T]): Without[T] = new Without[T](values)
	def out[T](values: String*): Without[String] = new Without[String](values.toArray)
	def out[T](value: String): Without[String] = new Without[String](Array(value))
	
}

class WithOr[T](val values: Array[T]) extends Qualifier {}
class WithAnd[T](val values: Array[T]) extends Qualifier {}
class Without[T](val values: Array[T]) extends Qualifier {}

