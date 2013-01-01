package rscore.tests.dsl.collection
import rscore.tests.dsl.DSLBaseTest
import org.junit.Test
import rscore.dsl.entity.RSProject
import org.junit.Assert._
import org.junit.Before
import org.eclipse.jdt.core.dom.Type
import org.eclipse.jdt.core.dom.SimpleType
import org.junit.After
import rscore.util.FileUtil
import org.eclipse.core.runtime.NullProgressMonitor

/**
 * RSClasses test mainly for a hierarchy such as superclass or super_interface
 */
class RSClassesTest_Superclass extends DSLBaseTest {
	override val testGroupIdentifier = "find_test_classes.hierarchy_a"
	private var project: RSProject = _

	@Before
	override def setUp: Unit = {
		super.setUp()
		val extPackageName = "find_test_classes.hierarchy.other"
		val pkg = addPackage(extPackageName)
		
		val inputFilepath = "test_resources_input/" + extPackageName.replaceAll("""\.""", "/") + "/" + "Hoge.java"
		val inputSource = FileUtil.getFileContents(inputFilepath)
		
		addCompilationUnit("Hoge", inputSource, pkg)
		
		this.project = $.project(this.projectName)
	}

	@Test
	def sandbox: Unit = {
		val testName = "UnresolvedExtendsInner"
		prepareTest(testName)

		val cls = project.pkg(testGroupIdentifier).classes().first
		println(cls.getDeclaration().getSuperclassType().resolveBinding().getQualifiedName())
		println(cls.getDeclaration().getSuperclassType().resolveBinding().getPackage().getName())
	}
	
	@Test
	def スーパークラスを取得する(): Unit = {
		val testName = "UnresolvedExtendsInner"
		prepareTest(testName)
		val cls = project.pkg(testGroupIdentifier).classes().first
		
		assertEquals(testName, cls.name)
		assertTrue(cls.hasSuperclass())
		assertNotNull(cls.superclass())
		
	}

	/**
	 * Type:
	 *    PrimitiveType
	 *    ArrayType
	 *    SimpleType
	 *    QualifiedType
	 *    ParameterizedType
	 *    WildcardType
	 * PrimitiveType:
	 *    byte
	 *    short
	 *    char
	 *    int
	 *    long
	 *    float
	 *    double
	 *    boolean
	 *    void
	 * ArrayType:
	 *    Type [ ]
	 * SimpleType:
	 *    TypeName
	 * ParameterizedType:
	 *    Type < Type { , Type } >
	 * QualifiedType:
	 *    Type . SimpleName
	 * WildcardType:
	 *    ? [ ( extends | super) Type ]
	 */

	def a(t: Type): Unit = {
		if (t.isArrayType()) println("array")
		if (t.isParameterizedType()) println("parameterized")
		if (t.isPrimitiveType()) println("primitive")
		if (t.isQualifiedType()) println("qualified")
		if (t.isSimpleType()) println("simple")
		if (t.isWildcardType()) println("wildcard")
	}

}