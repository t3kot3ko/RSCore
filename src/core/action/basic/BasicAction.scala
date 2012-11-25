package core.action.basic
import core.action.AbstractAction

class BasicAction(changes: Seq[() => Unit]) extends AbstractAction(changes){

}