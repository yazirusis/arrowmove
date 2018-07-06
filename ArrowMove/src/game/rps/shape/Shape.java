package game.rps.shape;

import java.util.Arrays;
import java.util.Iterator;

public enum Shape {
	ROCK("グー"),
	PAPER("パー"),
	SCISSOR("チョキ");

	public static RPSResult[] judgeAll(Shape ...shapes) {
		if (shapes.length < 2) throw new IllegalArgumentException("手が2つ以上ありません");
		RPSResult[] ret = new RPSResult[shapes.length];
		boolean[] ex = new boolean[3];
		for (Shape s:shapes) {
			ex[s.ordinal()] = true;
		}
		RPSResult[] results = new RPSResult[3];
		Arrays.fill(results, RPSResult.DRAW);
		for (int i=0; i < 3; i++) {
			if (ex[i] && ex[(i+1)%3] && !ex[(i+2)%3]) {//ある、ある、なし
				results[i] = RPSResult.LOSE;
				results[(i+1)%3] = RPSResult.WIN;
			}
		}
		for (int i=0;i < shapes.length; i++) {
			ret[i] = results[shapes[i].ordinal()];
		}
		return ret;
	}
	
	private String name;
	private Shape(String name) {
		this.name = name;
	}
	/**
	 * この手が引数の手に勝つかどうか
	 * @param shape
	 * @return
	 */
	public RPSResult judge(Shape shape) {
		int c = (this.ordinal() - shape.ordinal()+3)%3;
		if (c == 0) return RPSResult.DRAW;
		else if (c==1) return RPSResult.WIN;
		else if (c==2) return RPSResult.LOSE;
		return null;
	}
	public String getName() {
		return name;
	}
	
}
