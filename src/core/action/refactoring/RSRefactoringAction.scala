package core.action.refactoring
import core.action.AbstractAction

class RSRefactoringAction(val changes: Seq[() => Unit]) extends AbstractAction(changes){
}