package rscore.action.refactoring
import rscore.action.RSAbstractAction

class RSRefactoringAction(val changes: Seq[() => Unit]) extends RSAbstractAction(changes){
}