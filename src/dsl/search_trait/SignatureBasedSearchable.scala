package dsl.search_trait

trait SignatureBasedSearchable{
	val signature: Array[String]
	
	def hasSignature(signature: Array[String]): Boolean = {
		return Range(0, signature.length).forall(i => signature(i) == signature(i))
	}
	
	def hasSignaturesOr(signatures: Array[Array[String]]): Boolean = {
		return signatures.exists(hasSignature(_))
	}
	
}