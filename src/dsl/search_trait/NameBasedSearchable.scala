package dsl.search_trait
import scala.util.matching.Regex

trait NameBasedSearchable {
	// エンティティ名の取得方法がそれぞれで一緒なら，ここで具体的な取得方法を書いて，実装クラスへの（での）委譲を消す
	val name: String
	
	// 名前の完全一致
	private def hasName(name: String): Boolean = {
		return this.name == name
	}
	
	// 正規表現で一致
	// TODO: もっといいマッチ検査方法があるはず
	private def hasRegexMatchedName(reg: Regex): Boolean = {
		reg findPrefixOf this.name match{
			case Some(_) => return true
			case None => return false
		}
	}
	
	def hasNamesOr(names: Array[String]): Boolean = {
		return names.exists(hasName(_))
	}
	
	// Array[String], Array[Regex] を受け付けられる拡張 RSParams を作るまでは使えない
	def hasRegexeMathcedNamesOr(nameRegexes: Array[Regex]): Boolean ={
		return nameRegexes.exists(hasRegexMatchedName(_))
	}
	
	// いつか消す
	def hasRegexeMathcedNamesOr(nameRegexStrings: Array[String]): Boolean ={
		return nameRegexStrings.exists(e => hasRegexMatchedName(e.r))
	}
}