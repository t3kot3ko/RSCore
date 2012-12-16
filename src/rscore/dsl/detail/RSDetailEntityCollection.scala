package rscore.dsl.detail

/**
 * DetailEntity (Body 以下の要素)の集合を表すクラスの基底クラス
 * 基本的なリストアクセッサなどをラップしたメソッドを提供する
 */
case class RSDetailEntityCollection[T <: RSDetailEntity](elements: List[T]) {
	def length: Int = elements.length
	def size: Int = this.length
	def cont: Int = this.length
	
	def first = elements.first
	def apply(index: Int) = elements(index)
	
}