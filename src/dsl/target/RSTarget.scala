package dsl.target
import dsl.entity.RSEntity
import dsl.entity.RSWorkspace

/**
 * アクションのターゲットになるエンティティ
 * （多分，RSEntity の集合（のラップ））
 * target.action(Processor).perform
 * fields.toTarget.action("refactoring", "rename").param(...).param(...).......perform()
 */
class RSTarget(val id: String, val entity: RSEntity[_], val parameters: (String, String)){
	// パラメータが要らない場合
	def this(id: String, entity: RSEntity[_]) = this(id, entity, null) 
	
	// id すらいらない場合
	def this(entity: RSEntity[_]) = this("", entity, null)
}