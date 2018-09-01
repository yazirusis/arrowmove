package game.data

import game.controller.Controller
import game.system.Game

class TextCommandData(controller: Controller<*>, private val text: String) : CommandData(controller) {
    override fun toString(): String {
        return text
    }

    @Override
    override fun toTextCommandData(): TextCommandData {
        return TextCommandData(controller, text)
    }
//
//    @Override
//    fun execute(game: Game<*>) {
//        game.makeCommandData(this).execute(game)
//    }
//
//    @Override
//    fun undo(game: Game<*>) {
//        game.makeCommandData(this).undo(game)
//
//    }
}
