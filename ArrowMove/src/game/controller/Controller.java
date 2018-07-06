package game.controller;

import game.data.CommandData;
import game.data.Data;
import game.data.TextCommandData;

public abstract class Controller<IN extends Data> {
	private String name;
	
	public Controller(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public abstract void open();
	
	public abstract CommandData controll(IN data);
	
	public abstract void close();

}
