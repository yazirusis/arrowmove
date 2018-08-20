package game.data;

import game.controller.Controller;
import game.system.Game;

public abstract class CommandData<T extends Game> extends Data {
	protected Controller controller;

	public CommandData(Controller controller) {
		this.controller = controller;
	}
	
	public abstract TextCommandData toTextCommandData();
	
	public Controller getController() {
		return controller;
	}
	
	//実行
	public abstract void execute(T game);
	//もとに戻す
	public abstract void undo(T game);
	
	
	
	

}
