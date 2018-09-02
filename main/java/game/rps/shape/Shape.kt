package game.rps.shape

import java.util.Arrays

enum class Shape private constructor(val shapeName: String) {
    ROCK("グー"),
    PAPER("パー"),
    SCISSOR("チョキ");

    /**
     * この手が引数の手に勝つかどうか
     * @param shape
     * @return
     */
    fun judge(shape: Shape): RPSResult? {
        val c = (this.ordinal - shape.ordinal + 3) % 3
        if (c == 0)
            return RPSResult.DRAW
        else if (c == 1)
            return RPSResult.WIN
        else if (c == 2) return RPSResult.LOSE
        return null
    }

    companion object {

        fun judgeAll(vararg shapes: Shape?): Array<RPSResult> {
            if (shapes.size < 2) throw IllegalArgumentException("手が2つ以上ありません")
            val ret = Array<RPSResult>(shapes.size,{RPSResult.DRAW})
            val ex = BooleanArray(3)
            for (s in shapes) {
                ex[s!!.ordinal] = true
            }
            val results = Array<RPSResult>(3,{RPSResult.DRAW})
            for (i in 0..2) {
                if (ex[i] && ex[(i + 1) % 3] && !ex[(i + 2) % 3]) {//ある、ある、なし
                    results[i] = RPSResult.LOSE
                    results[(i + 1) % 3] = RPSResult.WIN
                }
            }
            for (i in shapes.indices) {
                ret[i] = results[shapes[i]!!.ordinal]
            }
            return ret
        }
    }

}
