package rscore.action.refactoring
import rscore.action.AbstractAction

class RSRefactoringAction(val changes: Seq[() => Unit]) extends AbstractAction(changes){
}