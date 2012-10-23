package dsl.query
import dsl.search_trait.NameBasedSearchable

case class NameQuery(val names: Array[String]) extends RSQuery{
}