package game.rps.data;

import game.controller.Controller;
import game.data.CommandData;
import game.data.Data;
import game.data.TextCommandData;
import game.rps.shape.Shape;

public class RPSCommandData extends CommandData{
	private Shape shape;
	public RPSCommandData(Controller controller,Shape shape) {
		super(controller);
		this.shape = shape;
	}
	public RPSCommandData(TextCommandData data) {
		super(data.getController());
		this.shape = Shape.valueOf(data.toString());
	}
	
	public Shape getShape() {
		return shape;
	}

	@Override
	public TextCommandData toTextCommandData() {
		String text = String.format("%s", shape);
		return new TextCommandData(controller,text);
	}

	
}
