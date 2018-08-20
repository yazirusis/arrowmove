package game.data;

import game.controller.Controller;
import game.system.Game;

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
	@Override
	public void execute(Game game) {
		game.makeCommandData(this).execute(game);
	}
	@Override
	public void undo(Game game) {
		game.makeCommandData(this).undo(game);
		
	}
}
