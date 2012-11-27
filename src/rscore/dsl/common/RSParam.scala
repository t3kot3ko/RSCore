package rscore.dsl.common

case class RSParam[T](val value: (String, Array[T])) {
	def and(other: RSParam[_]): Array[RSParam[_]] = {
		return Array[RSParam[_]](this, other)
	}
}

object RSParam {
	/**
	 * RSParam 単体でも配列扱いできるようにする
	 */
	implicit def convertToArray(rsParam: RSParam[_]): Array[RSParam[_]] = {
		return Array(rsParam)
	}

	/**
	 * Array[RSParam[_]] に対して，and メソッドを呼べるようにする
	 * 内部的には1回 Set に戻して，末尾に要素を突っ込んで，配列に直す
	 */
	implicit def extendRSParamArray(rsParams: Array[RSParam[_]]) = new {
		def and(other: RSParam[_]): Array[RSParam[_]] = {
			return (rsParams.toSet + other).toArray
		}
	}
}