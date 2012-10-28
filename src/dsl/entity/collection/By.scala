package dsl.entity.collection
import dsl.query.NameQuery
import scala.util.matching.Regex
import dsl.query.NameRegQuery
import dsl.query.ModifierQuery
import dsl.query.CallbackQuery
import dsl.entity.RSEntity
import dsl.query.TypeQuery

object By {
	/*
	 * find(By.***()) の *** の部分に相当する文字列（メソッド名）と，対応するクエリオブジェクト(**Query)をひもづける
	 * RSQuery のファクトリ（と見ることもできる）
	 */
	def Name(q: Qualifier): NameQuery = NameQuery(q)
	def Namereg(q: Qualifier): NameRegQuery = NameRegQuery(q)
	def Modifier(q: Qualifier): ModifierQuery = ModifierQuery(q)
	def Type(q: Qualifier): TypeQuery = TypeQuery(q)
	def Callback[T <: RSEntity](q: Qualifier): CallbackQuery[T] = CallbackQuery[T](q)
}