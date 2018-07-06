package game.data;

import game.controller.Controller;

public abstract class CommandData extends Data {
	protected Controller controller;

	public CommandData(Controller controller) {
		this.controller = controller;
	}
	
	public abstract TextCommandData toTextCommandData();
	
	public Controller getController() {
		return controller;
	}
	
	

}
