package pathUtil

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException

object PathUtil {

    /**
     * 同じパスでjar内とEclipse両方に対応させる
     * Ecliseではsrc内にファイルを置く
     * @param path
     * @return
     * @throws UnsupportedEncodingException
     */
    fun getBufferedReader(path: String): BufferedReader? {
        val `is` = ClassLoader.getSystemResourceAsStream(path) ?: return null
        var `in`: BufferedReader? = null
        try {
            `in` = BufferedReader(InputStreamReader(`is`, "UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace()
        }

        return `in`
    }
}
