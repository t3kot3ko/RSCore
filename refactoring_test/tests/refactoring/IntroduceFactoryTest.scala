package tests.refactoring
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.jdt.core.IPackageFragment
import core.util.FileUtil
import org.junit.Before
import org.junit.Assert
import org.junit.Test
import org.eclipse.core.runtime.NullProgressMonitor
import org.junit.After
import dsl.entity.RSWorkspace
import dsl.util.ImplicitConversions._
import dsl.entity.collection.By
import dsl.entity.collection.With
import org.junit.Ignore
import java.io.FileNotFoundException

class IntroduceFactoryTest extends RefactoringBaseTest {
	override val testGroupIdentifier = "introduce_factory"

	@Before
	override def setUp(): Unit = {
		super.setUp()
	}

	@Test
	def クラス定義のみ(): Unit = {
		val testName = "DeclarationOnly"
		prepareTest(testName)

		val $ = RSWorkspace
		$.project(projectName).pkg(testGroupIdentifier).classes.select(By.Name(testName)).first.introduce_factory
		
		doAssert(testName)
	}

	@Test
	def クラス定義とコンストラクタ呼び出し() = {
		val testName = "DeclarationAndCaller"
		prepareTest(testName)

		val $ = RSWorkspace
		$.project(projectName).pkg(testGroupIdentifier).classes.select(By.Name(testName)).first.introduce_factory
		
		doAssert(testName)
	}
	
	@Test
	def コンストラクタを指定してクラス定義のみをリファクタリング(): Unit = {
		val testName = "DeclarationOnly"
		prepareTest(testName)

		val $ = RSWorkspace
		$.project(projectName).pkg(testGroupIdentifier).classes.select(By.Name(testName)).first.constructors.first.introduce_factory
		
		doAssert(testName)
		
	}
	
	@Test
	def コンストラクタを指定してクラス定義とコンストラクタ呼び出しをリファクタリング(): Unit = {
		val testName = "DeclarationAndCaller"
		prepareTest(testName)

		val $ = RSWorkspace
		$.project(projectName).pkg(testGroupIdentifier).classes.select(By.Name(testName)).first.introduce_factory
		
		doAssert(testName)
	}
	
	
	@Test
	def 複数のコンストラクタがある場合(): Unit = {
		val testName = "MultipleConstructor"
		prepareTest(testName)
	}


	/*
	@After
	override def tearDown(): Unit = {
		println("tearing down without removing generated files...")
	}
	*/

}