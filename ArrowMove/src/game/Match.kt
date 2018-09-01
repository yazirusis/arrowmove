package game

import game.controller.Controller
import game.controller.group.ControllerGroup
import game.data.Data
import game.system.Game
import game.value.FinishStatus

class Match(protected var game: Game<*,*>) {

    //終了したか
    val finishStatus: FinishStatus
        get() = game.finishStatus

    //ゲームをプレイ
    fun play() {
        open()
        while (!finishStatus.isFinish) {
            next()
        }
        close()
    }

    //回数指定でゲームをプレイ
    fun play(num: Int) {
        for (i in 0 until num) {
            play()
        }
    }

    //ゲーム開始
    /*protected*/ fun open() {
        game.open()
    }

    //更新
    /*protected*/ operator fun next() {
        game.next()
    }

    //終了処理
    /*protected*/ fun previous() {
        game.previous()
    }

    //終了処理
    /*protected*/ fun close() {
        game.close()
    }


}
