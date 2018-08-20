package game.rps.data;

import game.controller.Controller;
import game.data.CommandData;
import game.data.Data;
import game.data.TextCommandData;
import game.rps.RPS;
import game.rps.shape.Shape;
import game.system.Game;

public class RPSCommandData extends CommandData<RPS>{
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
	@Override
	public void execute(RPS game) {
		game.addShape(shape);
		if (game.isAllExecute()) {
			//全員分の手が出たらjudge
			game.judge();
		}
	}
	@Override
	public void undo(RPS game) {
		if (game.isAllExecute()) {
			//全員分の手が出たらjudge結果を戻す
			game.judge();
		}
		game.removeShape();
		
	}

	
}
