package game.rps

import java.util.ArrayList

import game.controller.group.ControllerGroup
import game.data.Data
import game.data.TextCommandData
import game.rps.data.RPSCommandData
import game.rps.data.RPSStateData
import game.rps.shape.RPSResult
import game.rps.shape.Shape
import game.system.Game
import game.value.FinishStatus
import org.jetbrains.annotations.Mutable

//じゃんけん
class RPS(group: ControllerGroup<RPSStateData>) : Game<RPSCommandData,RPSStateData>(group) {

    private var gameTime: Int = 0
    private var currentTime: Int = 0
    private lateinit var shapeLists: Array<MutableList<Shape?>>
    private lateinit var currentShape: Array<Shape?>
    private var winNums: IntArray? = null
    /**
     * すべてのプレイヤーの手が出たか
     * @return
     */
    //nullならまだ手が出てない
    val isAllExecute: Boolean
        get() {
            for (s in currentShape!!) {
                if (s == null) return false
            }
            return true
        }
    /**
     * 勝敗判定の直後か
     * @return
     */
    //null以外ならjudge直後じゃない
    val isJustAfterJudge: Boolean
        get() {
            for (s in currentShape!!) {
                if (s != null) return false
            }
            return true
        }

    init {
        init()

    }

    private fun init() {
        this.gameTime = properties.getPropertyInt(GMAE_TIME, 1)
        //gameTime = 1000;
    }

    @Override
    override protected fun gameOpen() {

        currentShape = arrayOfNulls<Shape>(group.size())
        currentTime = 0
        shapeLists = Array<MutableList<Shape?>>(group.size(), {mutableListOf<Shape?>()})
//        for (i in 0 until group.size()) {
//            shapeLists[i] = ArrayList<Shape>()
//        }
        winNums = IntArray(group.size())
        show()
    }

    /*@Override
	protected void gameNext() {
		currentShape = new Shape[group.size()];
		System.out.println((currentTime+1) + "回目");
		//手を出す
		for (int i=0; i < group.size();i++) {
			currentShape[i] = controll(i, new RPSStateData(shapeLists, i)).getShape();
			System.out.printf("%sさん、%s\n",group.getShapeName(i),currentShape[i].getShapeName() );
		}
		//勝敗判定

		RPSResult[] resurt = Shape.judgeAll(currentShape);
		System.out.print("今回は");
		boolean isWinner = false;
		for (int i=0; i < group.size(); i++) {
			if (resurt[i] == RPSResult.WIN) {
				winNums[i]++;
				System.out.print("、"+group.getShapeName(i));
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
    override fun execute(data: RPSCommandData) {
        addShape(data.shape)
        if (isAllExecute) {
            //全員分の手が出たらjudge
            judge()
            show()
        }

    }

    @Override
    override fun undo(data: RPSCommandData) {

        if (isJustAfterJudge) {
            //judge結果戻す
            undoJudge()
        }
        removeShape()
        if (isJustAfterJudge) {
            //手を消したあとにjudge前ならそこで表示
            show()
        }
    }

    /**
     * 現在プレイヤーの手を追加
     * @param shape
     */
    fun addShape(shape: Shape) {
        currentShape[currentControllerIndex] = shape
    }

    /**
     * 現在プレイヤーの手を消去
     */
    fun removeShape() {
        currentShape[currentControllerIndex] = null
    }

    fun judge() {
        //勝敗判定
        val resurt = Shape.judgeAll(*currentShape)
        //System.out.print("今回は");
        var isWinner = false
        for (i in 0 until group.size()) {
            if (resurt[i] === RPSResult.WIN) {
                winNums!![i]++
                //System.out.print("、"+group.getShapeName(i));
                isWinner = true
            }

        }
        //System.out.println(isWinner?"の勝ち":"引き分け");
        addShapeList()
        currentTime++
        //規定回数やったら終了
        if (gameTime == currentTime) finishStatus=FinishStatus(true)

    }

    /**
     * 現在の手をshapeListに登録
     */
    fun addShapeList() {
        for (i in 0 until group.size()) {
            //手を登録
            shapeLists!![currentTime].add(currentShape!![i])
        }
        //現在の手を初期化
        currentShape = arrayOfNulls<Shape>(group.size())
    }

    /**
     * hapeListの最後の手をcurrentShapeに戻す
     */
    fun removeShapeList() {
        for (i in 0 until group.size()) {
            //最後の値を消してcurrentSHapeに戻す
            currentShape[i] = shapeLists!![currentTime - 1].removeAt(0)
        }
    }

    //勝数を戻す
    fun undoJudge() {
        removeShapeList()
        //勝敗判定で勝数を戻す
        val resurt = Shape.judgeAll(*currentShape)
        for (i in 0 until group.size()) {
            if (resurt[i] === RPSResult.WIN) {
                winNums!![i]--
            }
        }

        currentTime--
        //終了状態を戻す
        if (gameTime != currentTime) finishStatus=FinishStatus(false)

    }

    @Override
    override protected fun gameClose() {

    }

    @Override
    override fun makeCommandData(data: TextCommandData): RPSCommandData {
        return RPSCommandData(data)
    }

    @Override
    override fun makeStateData(): Data {
        //TODO 実装途中　なににつかうんだったっけ？
        return RPSStateData(shapeLists,0)
    }

    @Override
    override fun showState() {
        //開始メッセージ
        if (currentTime == 0) {
            System.out.println("じゃんけんゲーム開始!!")
            System.out.println(gameTime.toString() + "回勝負!")
            return
        }
        val currentList = shapeLists!![currentTime - 1]
        //手を表示
        for (i in 0 until group.size()) {
            System.out.printf("%sさん、%s\n", group.getName(i), currentList[i]!!.name)
        }
        //勝敗判定
        val resurt = Shape.judgeAll(*currentList.toTypedArray())
        System.out.print("今回は")
        var isWinner = false
        for (i in 0 until group.size()) {
            if (resurt[i] === RPSResult.WIN) {
                System.out.print("、" + group.getName(i))
                isWinner = true
            }

        }
        System.out.println(if (isWinner) "の勝ち" else "引き分け")
        for (i in 0 until group.size()) {
            System.out.printf("%sさん、%d勝\n", group.getName(i), winNums!![i])
        }

        //終了メッセージ
        if (currentTime == gameTime) {
            System.out.println("じゃんけんゲーム終了!!")
            System.out.println("結果")
            for (i in 0 until group.size()) {
                System.out.printf("%sさん、%d勝\n", group.getName(i), winNums!![i])
            }
        }

    }

    companion object {
        val GMAE_TIME = "gametime"//1ゲームの試合数
    }

}
