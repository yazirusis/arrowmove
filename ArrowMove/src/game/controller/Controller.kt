package game.controller

import game.data.CommandData
import game.data.Data
import game.data.TextCommandData

abstract class Controller<in IN : Data>(val name: String) {

    abstract fun open()

    abstract fun controll(data: IN): CommandData

    abstract fun close()

}
