package game.controller.group;

import game.controller.Controller;
import game.data.CommandData;
import game.data.Data;

public class ControllerGroup {
	private Controller[] controllers;
	
	public ControllerGroup(Controller... controllers) {
		this.controllers = controllers;
	}

	public  int size() {
		return controllers.length;
	}
	
	public void open() {
		for (Controller c:controllers) {
			c.open();
		}
	}
	
	//とりあえず作った。TODO 順番もこのクラスで管理したい
	public CommandData controll(int i,Data data) {
		return controllers[i].controll(data);
	}
	public String getName(int i) {
		return controllers[i].getName();
	}
	
	public  void close() {
		for (Controller c:controllers) {
			c.close();
		}
	}
}
