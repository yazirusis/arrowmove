package game.controller

import java.io.*
import java.lang.ProcessBuilder.Redirect

import game.data.Data
import game.data.TextCommandData

//プロセス用のController
class ProcessController(command: String) : Controller<Data>(command) {
    private val processBuilder: ProcessBuilder
    private var process: Process? = null
    private var reader: BufferedReader? = null
    private var writer: BufferedWriter? = null

    init {
        processBuilder = ProcessBuilder(command.split(" "))//コマンドを空白で分割
        processBuilder.redirectError(Redirect.INHERIT)//TODO エラー出力を標準出力にとりあえずリダイレクト
    }

    @Override
    override fun open() {
        try {
            process = processBuilder.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        reader = BufferedReader(InputStreamReader(process!!.getInputStream()))
        writer = BufferedWriter(OutputStreamWriter(process!!.getOutputStream()))


    }

    @Override
    override fun controll(data: Data): TextCommandData {
        var command = ""
        try {
            //ゲームのデータを出力
            for (s in data.toString().split("\n")) {
                writer!!.write(s)
                writer!!.newLine()
            }
            writer!!.flush()
            //Processの行動を入力
            var line: String? = null
            while (true) {

                line = reader!!.readLine()
                if (line == null || isEndLine(line!!)) break
                command += line!! + "\n"
            }


        } catch (e: IOException) {
            e.printStackTrace()
        }

        return TextCommandData(this, command)
    }

    private fun isEndLine(line: String): Boolean {
        return line.equals(READ_END)
    }

    @Override
    override fun close() {
        try {
            reader!!.close()
            writer!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }


    }

    companion object {
        val READ_END = "end"
    }

}
