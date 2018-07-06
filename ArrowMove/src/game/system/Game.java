package game.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

import pathUtil.PathUtil;
import game.controller.group.ControllerGroup;
import game.data.CommandData;
import game.data.Data;
import game.data.TextCommandData;
import game.rps.RPS;
import game.value.FinishStatus;

//ゲーム本体のクラス
public abstract class Game <IN extends CommandData>{
	protected ControllerGroup group;//プレイヤーグループ
	protected GameProperties properties;//ゲーム設定
	protected FinishStatus finishStatus = new FinishStatus(false);
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

	public void open() {
		group.open();
		gameOpen();
	}
	protected abstract void gameOpen();

	public void next() {
		if (getFinishStatus().isFinish()) return;//ゲームが終わってるなら何もしない
		gameNext();
	}	
	protected abstract void gameNext();

	public void close() {
		gameClose();
		group.close();
	}
	protected abstract void gameClose();
	//終了したか
	public FinishStatus getFinishStatus() {
		return finishStatus;
	}
	protected void setFinishStatus(FinishStatus finishStatus){
		this.finishStatus = finishStatus;
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
	protected abstract IN makeCommandData(TextCommandData data) ;
}
