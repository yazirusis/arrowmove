package game.rps;

import java.util.ArrayList;
import java.util.List;

import game.controller.group.ControllerGroup;
import game.data.Data;
import game.data.TextCommandData;
import game.rps.data.RPSCommandData;
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
	private Shape[] currentShape;
	private int[] winNums;

	public RPS(ControllerGroup group) {
		super(group);
		init();

	}
	private void init() {
		this.gameTime = properties.getPropertyInt(GMAE_TIME, 1);
		//gameTime = 1000;
	}

	@Override
	protected void gameOpen() {

		currentShape = new Shape[group.size()];
		currentTime = 0;
		shapeLists = new List[group.size()];
		for (int i=0; i < group.size();i++){
			shapeLists[i] = new ArrayList<Shape>();
		}
		winNums = new int[group.size()];
		show();
	}
	/*@Override
	protected void gameNext() {
		currentShape = new Shape[group.size()];
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
			//手を登録
			shapeLists[i].add(currentShape[i]);
		}
		System.out.println(isWinner?"の勝ち":"引き分け");

		currentTime++;
		//規定回数やったら終了
		if (gameTime== currentTime) setFinishStatus(new FinishStatus(true));
	}*/
	@Override
	public void execute(RPSCommandData data) {
		addShape(data.getShape());
		if (isAllExecute()) {
			//全員分の手が出たらjudge
			judge();
			show();
		}

	}
	@Override
	public void undo(RPSCommandData data) {

		if (isJustAfterJudge()) {
			//judge結果戻す
			undoJudge();
		}
		removeShape();
		if (isJustAfterJudge()) {
			//手を消したあとにjudge前ならそこで表示
			show();
		}
	}
	/**
	 * 現在プレイヤーの手を追加
	 * @param shape
	 */
	public void addShape(Shape shape){
		currentShape[getCurrentControllerIndex()] = shape;
	}
	/**
	 * 現在プレイヤーの手を消去
	 */
	public void removeShape(){
		currentShape[getCurrentControllerIndex()] = null;
	}
	/**
	 * すべてのプレイヤーの手が出たか
	 * @return
	 */
	public boolean isAllExecute() {
		//nullならまだ手が出てない
		for (Shape s:currentShape){
			if (s == null)return false;
		}
		return true;
	}
	/**
	 * 勝敗判定の直後か
	 * @return
	 */
	public boolean isJustAfterJudge() {
		//null以外ならjudge直後じゃない
		for (Shape s:currentShape){
			if (s != null)return false;
		}
		return true;
	}
	public void judge() {
		//勝敗判定
		RPSResult[] resurt = Shape.judgeAll(currentShape);
		//System.out.print("今回は");
		boolean isWinner = false;
		for (int i=0; i < group.size(); i++) {
			if (resurt[i] == RPSResult.WIN) {
				winNums[i]++;
				//System.out.print("、"+group.getName(i));
				isWinner = true;
			}

		}
		//System.out.println(isWinner?"の勝ち":"引き分け");
		addShapeList();
		currentTime++;
		//規定回数やったら終了
		if (gameTime== currentTime) setFinishStatus(new FinishStatus(true));

	}

	/**
	 * 現在の手をshapeListに登録
	 */
	public void addShapeList() {
		for (int i=0; i < group.size();i++) {
			//手を登録
			shapeLists[currentTime].add(currentShape[i]);
		}
		//現在の手を初期化
		currentShape = new Shape[group.size()];
	}
	/**
	 * hapeListの最後の手をcurrentShapeに戻す
	 */
	public void removeShapeList() {
		for (int i=0; i < group.size();i++) {
			//最後の値を消してcurrentSHapeに戻す
			currentShape[i] = shapeLists[currentTime-1].remove(0);
		}
	}
	//勝数を戻す
	public void undoJudge() {
		removeShapeList();
		//勝敗判定で勝数を戻す
		RPSResult[] resurt = Shape.judgeAll(currentShape);
		for (int i=0; i < group.size(); i++) {
			if (resurt[i] == RPSResult.WIN) {
				winNums[i]--;
			}
		}

		currentTime--;
		//終了状態を戻す
		if (gameTime!= currentTime) setFinishStatus(new FinishStatus(false));

	}

	@Override
	protected void gameClose() {

	}
	@Override
	public RPSCommandData makeCommandData(TextCommandData data) {
		return new RPSCommandData(data);
	}
	@Override
	public Data makeStateData() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	@Override
	public void showState() {
		//開始メッセージ
		if (currentTime == 0) {
			System.out.println("じゃんけんゲーム開始!!");
			System.out.println(gameTime+"回勝負!");
			return;
		}
		List<Shape> currentList = shapeLists[currentTime-1];
		//手を表示
		for (int i=0; i < group.size();i++) {
			System.out.printf("%sさん、%s\n",group.getName(i)
					,currentList.get(i).getName());
		}
		//勝敗判定
		RPSResult[] resurt = Shape.judgeAll(
				currentList.toArray(new Shape[currentList.size()]));
		System.out.print("今回は");
		boolean isWinner = false;
		for (int i=0; i < group.size(); i++) {
			if (resurt[i] == RPSResult.WIN) {
				System.out.print("、"+group.getName(i));
				isWinner = true;
			}

		}
		System.out.println(isWinner?"の勝ち":"引き分け");
		for (int i=0; i < group.size();i++) {
			System.out.printf("%sさん、%d勝\n",group.getName(i),winNums[i]);
		}

		//終了メッセージ
		if (currentTime == gameTime) {
			System.out.println("じゃんけんゲーム終了!!");
			System.out.println("結果");
			for (int i=0; i < group.size();i++) {
				System.out.printf("%sさん、%d勝\n",group.getName(i),winNums[i]);
			}
		}

	}

}
