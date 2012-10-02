package core.sandbox
import scala.collection.mutable.Buffer
import org.eclipse.jdt.internal.corext.refactoring.ParameterInfo
import java.util.ArrayList
import core.helper.RefactoringHelper
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import core.helper.CUHelper
import org.eclipse.jdt.core.Signature
import org.eclipse.jdt.core.IMethod
import org.eclipse.jdt.internal.corext.refactoring.structure.ChangeSignatureProcessor
import scala.collection.JavaConverters._

object ChangeSignature {
	/**
	 * TODO: Ç‹ÇæçÏÇËìríÜ
	 */
	def changeSignatureSampe(): Unit = {
		var project = CUHelper.getJavaProject("Sample")
		var root = CUHelper.getSourceFolder(project)
		var pack = root.getPackageFragment("change_signature")
		var cu = CUHelper.getCompilationUnit(pack, "Foo")
		var typ = cu.getTypes().first

		var stringSignature = Signature.createTypeSignature("String", false)
		var method: IMethod = typ.getMethod("method", Array[String](Signature.SIG_INT, stringSignature))
		assert(method != null)
		var processor = new ChangeSignatureProcessor(method)
		assert(processor != null)

		processor.setNewMethodName("newMethod")
		var newOrder = Array[String]("j", "i")
		var signature = Array[String]("I", "I")

		modifyInfos(processor.getParameterInfos().asScala, newOrder, null, null)

		var createDeligate = false
		processor.setDelegateUpdating(createDeligate)

		var refactoring = new ProcessorBasedRefactoring(processor)

		RefactoringHelper.performRefactoring(refactoring)

	}
	
	// --------- helper
	private def modifyInfos(infos: Buffer[ParameterInfo], newOrder: Array[String], oldNames: Array[String], newNames: Array[String]): Unit = {
		var permutation = createPermutation(infos, newOrder)
		var swapped = new ArrayList(infos.length)
		if (oldNames == null || newNames == null) {
			var newInfos: Array[ParameterInfo] = new Array[ParameterInfo](infos.length)
			for (i <- 0 until permutation.length) {
				newInfos(i) = infos(permutation(i))
			}
			infos.clear()
			for (i <- 0 until newInfos.length) {
				infos += newInfos(i)
			}

		}
	}

	private def swap(infos: Buffer[ParameterInfo], i: Int, ii: Int): Unit = {
		var o = infos(i)
		infos(i) = infos(ii)
		infos(ii) = o
	}
	private def createPermutation(infos: Buffer[ParameterInfo], newOrder: Array[String]): Array[Int] = {
		var result: Array[Int] = new Array[Int](infos.length)
		for (i <- 0 until result.length) {
			result(i) = indexOfOldName(infos, newOrder(i))
		}
		return result
	}
	private def indexOfOldName(infos: Buffer[ParameterInfo], string: String): Int = {
		for (i <- infos) {
			if (i.getOldName() == string) {
				return infos.indexOf(i)
			}
		}
		return -1
	}
	// -------------------------------------

}