package game.data;

import game.controller.Controller;

public class TextCommandData extends CommandData {
	private String text;
	
	public TextCommandData(Controller controller,String text) {
		super(controller);
		this.text = text;
	}
	public String toString() {
		return text;
	};
	
	@Override
	public TextCommandData toTextCommandData() {
		return new TextCommandData(controller, text);
	}
}
