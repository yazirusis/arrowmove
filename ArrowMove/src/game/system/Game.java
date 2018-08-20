package game.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import pathUtil.PathUtil;
import game.controller.group.ControllerGroup;
import game.data.CommandData;
import game.data.Data;
import game.data.TextCommandData;
import game.rps.RPS;
import game.rps.data.RPSStateData;
import game.value.FinishStatus;

//ゲーム本体のクラス
public abstract class Game <IN extends CommandData>{
	protected ControllerGroup group;//プレイヤーグループ
	protected GameProperties properties;//ゲーム設定
	/**
	 * 終了ステータス
	 */
	protected FinishStatus finishStatus = new FinishStatus(false);
	/**
	 * 現在操作中のプレイヤーのインデックス
	 */
	protected int currentControllerIndex;
	/**
	 * アップデートフラグ。状態が変わったらTrueになって一旦終わる
	 */
	private boolean  isShow;
	/**
	 * コマンドを覚えておくリスト
	 */
	private List<IN> commandList;
	/**
	 * 最後に実行したコマンドのインデックス
	 */
	private int commandIndex;

	public Game(ControllerGroup group) {
		this.group = group;

		//System.out.println(this.getClass().getSimpleName());
		setProperties(this.getClass().getSimpleName());


	}
	public Game(ControllerGroup group, String propertiesPath) {
		this.group = group;
		setProperties(propertiesPath);
	}

	//プロパティのロード
	private void setProperties(String path) {
		this.properties = new GameProperties();

		BufferedReader br = PathUtil.getBufferedReader("properties/"+path);
		if (br != null) {
			try {
				this.properties.load(br);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	/**
	 * ゲーム開始
	 */
	public void open() {
		this.currentControllerIndex = 0;
		this.commandList = new ArrayList<IN>();
		this.commandIndex = -1;
		group.open();
		gameOpen();
	}
	/**
	 * ゲーム側で実装する開始処理
	 */
	protected abstract void gameOpen();

	/**
	 * ゲームを1ターン進める
	 */
	public void next() {

		if (getFinishStatus().isFinish()) return;//ゲームが終わってるなら何もしない
		isShow = false;
		//状態更新されるまで
		while(!isShow) {
			IN command;
			if (commandIndex+1 == commandList.size()) {
				//新しいコマンド要求
				command= controll(getCurrentControllerIndex(),makeStateData());
				commandList.add(command);

			}else {
				//コマンド履歴から実行
				command = commandList.get(commandIndex+1);
				
				
			}
			commandIndex++;
			execute(command);
			proceedControllerIndex();
		}

	}
	/**
	 * ゲームを1ターン戻す
	 */
	public void previous() {
		if (commandIndex < 0) return;//戻れないなら 
		isShow = false;
		//状態更新されるまで
		while(!isShow) {
			undo(commandList.get(commandIndex));
			
			commandIndex--;
			retreatControllerIndex();
			
		}



	}
	//コマンド実行
	public abstract void execute(IN data);
	//コマンドUNDO
	public abstract void  undo(IN data);
	//現在の状態のデータを受信
	public abstract Data makeStateData();
	//更新
	public  void  show() {
		showState();
		isShow = true;
	}
	//状態更新
	public abstract void  showState();


	public void close() {
		gameClose();
		group.close();
	}
	protected abstract void gameClose();
	//終了したか
	public FinishStatus getFinishStatus() {
		return finishStatus;
	}
	public void setFinishStatus(FinishStatus finishStatus){
		this.finishStatus = finishStatus;
	}
	//現在のプレイヤーのインデックスを返す
	public int getCurrentControllerIndex() {
		return currentControllerIndex;
	}
	//次のプレイヤーに順番を移す
	public void proceedControllerIndex() {
		currentControllerIndex++;
		if (group.size() == currentControllerIndex)currentControllerIndex=0;
	}
	//前のプレイヤーに順番を移す
	public void retreatControllerIndex() {
		currentControllerIndex--;
		if (-1 == currentControllerIndex)currentControllerIndex=group.size()-1;
	}

	protected IN controll(int i,Data data) {
		CommandData cd = group.controll(i, data);
		if (cd instanceof TextCommandData) {
			return makeCommandData((TextCommandData)cd);//TODO パースできなかったときの処理書く
		}else {
			return (IN)cd;
		}
	}
	//テキストコマンドデータからこのゲーム用のコマンドクラスを返すメソッド
	public abstract IN makeCommandData(TextCommandData data) ;
}
