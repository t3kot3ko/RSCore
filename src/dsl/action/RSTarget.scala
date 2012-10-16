package dsl.action
import dsl.entity.RSEntity
import dsl.entity.RSWorkspace

/**
 * アクションのターゲットになるエンティティ
 * （多分，RSEntity の集合（のラップ））
 * target.action(Processor).perform
 * fields.toTarget.action("refactoring", "rename").param(...).param(...).......perform()
 */
class RSTarget(val entities: Array[_]){
	def action(kind: String, id: String): RSAction = {
		return null
	}
	
	def syntaxCheck(): Unit = {
		var $ = RSWorkspace
		$.project("sample").getPackage("aaaaa")
	}
}