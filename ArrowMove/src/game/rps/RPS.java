package game.rps;

import java.util.ArrayList;
import java.util.List;

import game.controller.group.ControllerGroup;
import game.data.CommandData;
import game.data.TextCommandData;
import game.rps.data.RPSCommandData;
import game.rps.data.RPSStateData;
import game.rps.shape.RPSResult;
import game.rps.shape.Shape;
import game.system.Game;
import game.value.FinishStatus;
//じゃんけん
public class RPS extends Game<RPSCommandData> {
	public static final String GMAE_TIME = "gametime";//1ゲームの試合数

	private int gameTime;
	private int currentTime;
	private List<Shape>[] shapeLists;
	private int[] winNums;

	public RPS(ControllerGroup group) {
		super(group);
		init();

	}
	private void init() {
		this.gameTime = properties.getPropertyInt(GMAE_TIME, 1);
		//gameTime = 1000;
		currentTime = 0;
		shapeLists = new List[group.size()];
		for (int i=0; i < group.size();i++){
			shapeLists[i] = new ArrayList<Shape>();
		}
		winNums = new int[group.size()];
	}

	@Override
	protected void gameOpen() {
		System.out.println("じゃんけんゲーム開始!!");
		System.out.println(gameTime+"回勝負!");

	}
	@Override
	protected void gameNext() {
		Shape[] currentShape = new Shape[group.size()];
		System.out.println((currentTime+1) + "回目");
		//手を出す
		for (int i=0; i < group.size();i++) {
			
			currentShape[i] = controll(i, new RPSStateData(shapeLists, i)).getShape();
			System.out.printf("%sさん、%s\n",group.getName(i),currentShape[i].getName() );
		}
		//勝敗判定
		
		RPSResult[] resurt = Shape.judgeAll(currentShape);
		System.out.print("今回は");
		boolean isWinner = false;
		for (int i=0; i < group.size(); i++) {
			if (resurt[i] == RPSResult.WIN) {
				winNums[i]++;
				System.out.print("、"+group.getName(i));
				isWinner = true;
			}
		}
		System.out.println(isWinner?"の勝ち":"引き分け");
		
		currentTime++;
		//規定回数やったら終了
		if (gameTime== currentTime) setFinishStatus(new FinishStatus(true));
	}
	@Override
	protected void gameClose() {
		
		System.out.println("じゃんけんゲーム終了!!");
		System.out.println("結果");
		for (int i=0; i < group.size();i++) {
			System.out.printf("%sさん、%d勝\n",group.getName(i),winNums[i]);
		}
	}
	@Override
	protected RPSCommandData makeCommandData(TextCommandData data) {
		return new RPSCommandData(data);
	}

}
