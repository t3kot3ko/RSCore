package dsl.entity.collection
import dsl.query.NameQuery
import scala.util.matching.Regex
import dsl.query.NameRegQuery
import dsl.query.ModifierQuery
import dsl.query.ReturnTypeQuery
import dsl.query.CallbackQuery
import dsl.entity.RSEntity

object By {
	/*
	 * find(By.***()) の *** の部分に相当する文字列（メソッド名）と，対応するクエリオブジェクト(**Query)をひもづける
	 * RSQuery のファクトリ（と見ることもできる）
	 */
	def name(q: Qualifier): NameQuery = NameQuery(q)
	def namereg(q: Qualifier): NameRegQuery = NameRegQuery(q)
	def modifier(q: Qualifier): ModifierQuery = ModifierQuery(q)
	def returnType(q: Qualifier): ReturnTypeQuery = ReturnTypeQuery(q)
	def callback[T <: RSEntity](q: Qualifier): CallbackQuery[T] = CallbackQuery[T](q)
}