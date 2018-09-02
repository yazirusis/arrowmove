package game.controller.group

import game.controller.Controller
import game.data.CommandData
import game.data.Data

class ControllerGroup<in IN : Data>(vararg controllers: Controller<IN>) {
    private val controllers: Array<Controller<IN>>

    init {
        this.controllers = arrayOf(*controllers)
    }

    fun size(): Int {
        return controllers.size
    }

    fun open() {
        for (c in controllers) {
            c.open()
        }
    }

    //とりあえず作った。TODO 順番もこのクラスで管理したい
    fun controll(i: Int, data: IN): CommandData {
        return controllers[i].controll(data)
    }

    fun getName(i: Int): String {
        return controllers[i].name
    }

    fun close() {
        for (c in controllers) {
            c.close()
        }
    }
}
