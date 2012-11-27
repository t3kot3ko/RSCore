package rscore.dsl.traits.action
import rscore.dsl.entity.RSEntity
import rscore.dsl.common.RSObject

/**
 * リファクタリングアクションを提供するトレイト
 * DSL を整えるためだけのトレイトなので，具体的な処理実装は，ヘルパクラスに書くようにする
 */
trait RefactoringTrait {
	// レシーバへの参照が必要
	val self: RSObject
}