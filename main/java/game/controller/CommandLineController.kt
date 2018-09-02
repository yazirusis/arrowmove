package game.controller

import java.io.*
import java.lang.ProcessBuilder.Redirect

import game.data.CommandData
import game.data.Data
import game.data.TextCommandData

//プロセス用のController
class CommandLineController : Controller<Data>("commandLineController") {
    private var reader: BufferedReader? = null
    private var writer: BufferedWriter? = null

    @Override
    override fun open() {
        reader = BufferedReader(InputStreamReader(System.`in`))
        writer = BufferedWriter(OutputStreamWriter(System.out))
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
        /*try {
			reader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/


    }

    companion object {
        val READ_END = "end"
    }

}
