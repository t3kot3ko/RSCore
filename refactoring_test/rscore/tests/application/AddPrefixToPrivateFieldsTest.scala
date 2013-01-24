package rscore.tests.application
import org.junit.Test
import rscore.dsl.entity.collection.By
import rscore.dsl.entity.collection.With

class AddPrefixToPrivateFieldsTest extends ApplicationTest{
	@Test
	def 純粋privateなフィールドの先頭にプレフィクスがついていなければつける(): Unit = {
		val testName = "AddPrefixToPrivateFieldsTest"
			prepareTest(testName)
		
		getCurrentProject().pkg(this.testGroupIdentifier).classes().foreach(c => {
			c.fields.select(By modifier "private").
			select(By modifier (With out "static")).foreach(f => {
				val name = f.name
				if(name.charAt(0) != '_'){
					f.rename("_" + name)
				}
			})
		})
		
		doAssert(testName)
	}
	

}