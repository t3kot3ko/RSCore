package dsl.common

/**
 * Map[String, Array[String]] のラッパー(要らないかも）
 * TODO: 消す
 */
/*
case class RSParams(params: (String, Array[String])*){
	var value = Map.empty[String, Array[String]]
	for(param <- params){
		value += (param._1 -> param._2)
	}
	def getValue() = value
}
*/
case class RSParams(val value: RSParam[_]*){
		
}

object RSParams{
	// TODO: implicit conversion?
	// implicit def convertToRSParams(params: Map[String, Array[String]]) = new RSParams(params)
}