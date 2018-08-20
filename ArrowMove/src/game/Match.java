package game;

import game.controller.Controller;
import game.controller.group.ControllerGroup;
import game.data.Data;
import game.system.Game;
import game.value.FinishStatus;

public class Match {
	protected Game game;
	
	public Match(Game game) {
		this.game = game;
	}
	//ゲームをプレイ
	public void play() {
		open();
		while(!getFinishStatus().isFinish()) {
			next();
		}
		close();
	}
	//回数指定でゲームをプレイ
	public void play(int num) {
		for (int i=0; i < num; i++) {
			play();
		}
	}

	//ゲーム開始
	/*protected*/public void open() {
		game.open();
	}
	
	//更新
	/*protected*/public void next() {
		game.next();
	}
	//終了処理
	/*protected*/public void previous() {
		game.previous();
	}
	
	//終了処理
	/*protected*/public void close() {
		game.close();
	}
	
	//終了したか
	public  FinishStatus getFinishStatus() {
		return game.getFinishStatus();
	}
	
	

	
}
