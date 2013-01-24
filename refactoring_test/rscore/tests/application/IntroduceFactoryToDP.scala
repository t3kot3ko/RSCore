package rscore.tests.application
import org.junit.Test
import org.junit.Before
import org.junit.Assert._
import rscore.util.FileUtil
import rscore.dsl.entity.collection.By

class IntroduceFactoryToDP extends ApplicationTest {
	override val testGroupIdentifier = "application_introduce_factory"
	val clientPkgName = "application_introduce_factory_client"

	@Before
	override def setUp(): Unit = {
		super.setUp()
		val pkg = addPackage(clientPkgName)

		val filepath = "test_resources_input/" + clientPkgName.replaceAll("""\.""", "/") + "/" + "Client.java"
		val source = FileUtil.getFileContents(filepath)

		addCompilationUnit("Client", source, pkg)

		prepareTest("AbstractDescriptor")
		prepareTest("Descriptor1")
		prepareTest("Descriptor2")
		prepareTest("Descriptor3")

		// assertion for setup
		assertEquals(4, getCurrentProject().pkg(testGroupIdentifier).classes().length)
		assertEquals(1, getCurrentProject().pkg(clientPkgName).classes.length)
	}

	@Test
	def aaaa(): Unit = {
		val clss = getCurrentProject().pkg(testGroupIdentifier).classes().filter(_.hasSuperclass()).toArray.sortBy(e => e.name)
		println("---------------------")
		clss.foreach(c => println(c.name))
		println("---------------------")

		assertEquals(3, clss.length)

		clss.foreach(c => {
			c.introduce_factory(c.superclass())
		})

		val abstractClass = getCurrentProject().pkg(testGroupIdentifier).classes().selectOne(By modifier "abstract")
		val r = """createDescriptor(.*)""".r

		abstractClass.methods.select(By namereg """create.*""").foreach(m => {
			m.change_return_type(abstractClass.name)
			val newName = "for" + r.findAllIn(m.name).matchData.toArray.first.group(1)
			m.rename(newName)
		})

		doAssert("AbstractDescriptor")
		doAssert("Descriptor1")
		doAssert("Descriptor2")
		doAssert("Descriptor3")

		val clientExpected: String = {
			() =>
				{
					val filepath = "test_resources_output/" + clientPkgName.replaceAll("""\.""", "/") + "/" + "Client.java"
					FileUtil.eliminatePackageStatement(
						FileUtil.eliminateImportStatement(
							FileUtil.getFileContents(filepath, true)))
				}
		}.apply
		val clientActual = getCurrentProject().pkg(clientPkgName).classes.first.origin().getSource()
		doAssertHelper(clientExpected, clientActual)

	}

}