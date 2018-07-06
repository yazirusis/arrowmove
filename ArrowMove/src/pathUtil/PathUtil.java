package pathUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class PathUtil {

	/**
	 * 同じパスでjar内とEclipse両方に対応させる
	 * Ecliseではsrc内にファイルを置く
	 * @param path
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static BufferedReader getBufferedReader(String path) {
		InputStream is = ClassLoader.getSystemResourceAsStream(path);
		if (is == null) return null;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return in;
	}
}
