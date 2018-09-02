package game.rps.data

import game.controller.Controller
import game.data.CommandData
import game.data.Data
import game.data.TextCommandData
import game.rps.RPS
import game.rps.shape.Shape
import game.system.Game

class RPSCommandData : CommandData {
    var shape: Shape
        private set

    constructor(controller: Controller<*>, shape: Shape) : super(controller) {
        this.shape = shape
    }

    constructor(data: TextCommandData) : super(data.controller) {
        this.shape = Shape.valueOf(data.toString())
    }

    @Override
    override fun toTextCommandData(): TextCommandData {
        val text = String.format("%s", shape)
        return TextCommandData(controller, text)
    }

    @Override
    fun execute(game: RPS) {
        game.addShape(shape!!)
        if (game.isAllExecute) {
            //全員分の手が出たらjudge
            game.judge()
        }
    }

    @Override
    fun undo(game: RPS) {
        if (game.isAllExecute) {
            //全員分の手が出たらjudge結果を戻す
            game.judge()
        }
        game.removeShape()

    }


}
