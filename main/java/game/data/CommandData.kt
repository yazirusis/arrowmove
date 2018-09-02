package game.data

import game.controller.Controller
import game.system.Game

abstract class CommandData(controller: Controller<*>) : Data() {
    var controller: Controller<*>
        protected set

    init {
        this.controller = controller
    }

    abstract fun toTextCommandData(): TextCommandData
//
//    //実行
//    abstract fun execute(game: T)
//
//    //もとに戻す
//    abstract fun undo(game: T)


}
