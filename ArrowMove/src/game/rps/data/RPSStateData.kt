package game.rps.data

import game.controller.Controller
import game.data.CommandData
import game.data.Data
import game.rps.shape.Shape

class RPSStateData(val shapeLists: Array<MutableList<Shape?>>, val index: Int) : Data() {
    @Override
    override fun toString(): String {
        val sb = StringBuilder()
        //プレイヤー数　手数、自分の番号
        sb.append(String.format("%d %d %d\n", shapeLists.size, shapeLists[0].size, index))
        for (i in shapeLists.indices) {
            for (t in 0 until shapeLists[i].size) {
                sb.append(shapeLists[i][t])
                sb.append(" ")
            }
            sb.append("\n")
        }
        return sb.toString()
    }

}
