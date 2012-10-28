package dsl.traits.action
import dsl.entity.RSEntity
import dsl.common.RSObject

/**
 * リファクタリングアクションを提供するトレイト
 * DSL を整えるためだけのトレイトなので，具体的な処理実装は，ヘルパクラスに書くようにする
 */
trait RefactoringTrait {
	// レシーバへの参照が必要
	val self: RSObject
}