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
	 * 条件をひとつしか指定しないときは，By.Name("aaaa") のようにも書ける
	 */
	def Name(q: Qualifier): NameQuery = NameQuery(q)
	def Name(name: String): NameQuery = NameQuery(With.or(Array(name)))

	def Namereg(q: Qualifier): NameRegQuery = NameRegQuery(q)
	def Namereg(namereg: String): NameRegQuery = NameRegQuery(With.or(Array(namereg)))
	
	def Modifier(q: Qualifier): ModifierQuery = ModifierQuery(q)
	def Modifier(modifier: String): ModifierQuery = ModifierQuery(With.or(Array(modifier)))
	
	def Type(q: Qualifier): TypeQuery = TypeQuery(q)
	def Type(typeName: String): TypeQuery = TypeQuery(With.or(Array(typeName)))
	
	// def Callback[T <: RSEntity](q: Qualifier): CallbackQuery[T] = CallbackQuery[T](Array(q))
	// def Callback[T <: RSEntity](callback: T => Boolean): CallbackQuery[T] = CallbackQuery[T](With.or(callback))
}