package game.rps.controller;

import java.util.Random;

import game.controller.Controller;
import game.data.CommandData;
import game.rps.data.RPSCommandData;
import game.rps.data.RPSStateData;
import game.rps.shape.Shape;

public class RPSRandom extends Controller<RPSStateData> {
	private Random rand;

	public RPSRandom(String name) {
		super(name);
		this.rand = new Random();
		
	}

	@Override
	public void open() {
		
	}

	@Override
	public CommandData controll(RPSStateData data) {
		return new RPSCommandData(this
				,Shape.values()[rand.nextInt(Shape.values().length)]);
	}

	@Override
	public void close() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
