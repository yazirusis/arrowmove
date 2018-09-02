package game.system

import java.util.Properties

class GameProperties : Properties() {


    fun getPropertyInt(key: String): Int {
        val pr = super.getProperty(key) ?: return -1
        return Integer.parseInt(pr)
    }

    fun getPropertyInt(key: String, defaultValue: Int): Int {
        val pr = super.getProperty(key) ?: return defaultValue
        return Integer.parseInt(pr)
    }

}
