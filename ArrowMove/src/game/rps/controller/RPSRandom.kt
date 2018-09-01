package game.rps.controller

import java.util.Random

import game.controller.Controller
import game.data.CommandData
import game.rps.data.RPSCommandData
import game.rps.data.RPSStateData
import game.rps.shape.Shape

class RPSRandom(name: String) : Controller<RPSStateData>(name) {
    private val rand: Random

    init {
        this.rand = Random()

    }

    @Override
    override fun open() {

    }

    @Override
    override fun controll(data: RPSStateData): CommandData {
        return RPSCommandData(this, Shape.values()[rand.nextInt(Shape.values().size)]) as CommandData
    }

    @Override
    override fun close() {
        // TODO 自動生成されたメソッド・スタブ

    }

}
