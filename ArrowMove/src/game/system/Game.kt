package game.system

import java.io.BufferedReader
import java.io.IOException
import java.util.ArrayList
import java.util.Properties

import pathUtil.PathUtil
import game.controller.group.ControllerGroup
import game.data.CommandData
import game.data.Data
import game.data.TextCommandData
import game.rps.RPS
import game.rps.data.RPSStateData
import game.value.FinishStatus

//ゲーム本体のクラス
abstract class Game<IN : CommandData,OUT:Data> {
    protected var group: ControllerGroup<OUT>//プレイヤーグループ
    protected lateinit var properties: GameProperties//ゲーム設定
    /**
     * 終了ステータス
     */
    //終了したか
    var finishStatus = FinishStatus(false)
    /**
     * 現在操作中のプレイヤーのインデックス
     */
    //現在のプレイヤーのインデックスを返す
    var currentControllerIndex: Int = 0
        protected set
    /**
     * アップデートフラグ。状態が変わったらTrueになって一旦終わる
     */
    private var isShow: Boolean = false
    /**
     * コマンドを覚えておくリスト
     */
    private var commandList: MutableList<IN>? = null
    /**
     * 最後に実行したコマンドのインデックス
     */
    private var commandIndex: Int = 0

    constructor(group: ControllerGroup<OUT>) {
        this.group = group

        //System.out.println(this.getClass().getSimpleName());
        setProperties(this.javaClass.getSimpleName())


    }

    constructor(group: ControllerGroup<OUT>, propertiesPath: String) {
        this.group = group
        setProperties(propertiesPath)
    }

    //プロパティのロード
    private fun setProperties(path: String) {
        this.properties = GameProperties()

        val br = PathUtil.getBufferedReader("properties/$path")
        if (br != null) {
            try {
                this.properties.load(br)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

    }

    /**
     * ゲーム開始
     */
    fun open() {
        this.currentControllerIndex = 0
        this.commandList = ArrayList<IN>()
        this.commandIndex = -1
        group.open()
        gameOpen()
    }

    /**
     * ゲーム側で実装する開始処理
     */
    protected abstract fun gameOpen()

    /**
     * ゲームを1ターン進める
     */
    operator fun next() {
        if (finishStatus.isFinish) return //ゲームが終わってるなら何もしない
        isShow = false
        //状態更新されるまで
        while (!isShow) {
            val command: IN
            if (commandIndex + 1 == commandList!!.size) {
                //新しいコマンド要求
                command = controll(currentControllerIndex, makeStateData()as OUT)
                commandList!!.add(command)

            } else {
                //コマンド履歴から実行
                command = commandList!![commandIndex + 1]


            }
            commandIndex++
            execute(command)
            proceedControllerIndex()
        }

    }

    /**
     * ゲームを1ターン戻す
     */
    fun previous() {
        if (commandIndex < 0) return //戻れないなら
        isShow = false
        //状態更新されるまで
        while (!isShow) {
            undo(commandList!![commandIndex])

            commandIndex--
            retreatControllerIndex()

        }


    }

    //コマンド実行
    abstract fun execute(data: IN)

    //コマンドUNDO
    abstract fun undo(data: IN)

    //現在の状態のデータを受信
    abstract fun makeStateData(): Data

    //更新
    fun show() {
        showState()
        isShow = true
    }

    //状態更新
    abstract fun showState()


    fun close() {
        gameClose()
        group.close()
    }

    protected abstract fun gameClose()
    //次のプレイヤーに順番を移す
    fun proceedControllerIndex() {
        currentControllerIndex++
        if (group.size() === currentControllerIndex) currentControllerIndex = 0
    }

    //前のプレイヤーに順番を移す
    fun retreatControllerIndex() {
        currentControllerIndex--
        if (-1 == currentControllerIndex) currentControllerIndex = group.size() - 1
    }

    protected fun controll(i: Int, data: OUT): IN {
        val cd = group.controll(i, data)
        return if (cd is TextCommandData) {
            makeCommandData(cd as TextCommandData)//TODO パースできなかったときの処理書く
        } else {
            cd as IN
        }
    }

    //テキストコマンドデータからこのゲーム用のコマンドクラスを返すメソッド
    abstract fun makeCommandData(data: TextCommandData): IN
}
