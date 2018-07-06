package game.rps.data;

import java.util.List;

import game.controller.Controller;
import game.data.CommandData;
import game.data.Data;
import game.rps.shape.Shape;

public class RPSStateData extends Data{
	private List<Shape>[] shapeLists; 
	private int index;
	public RPSStateData(List<Shape>[] shapeLists,int index) {
		this.shapeLists =  shapeLists;
		this.index = index;
	}
	
	public List<Shape>[] getShapeLists() {
		return shapeLists;
	}
	
	public int getIndex() {
		return index;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//プレイヤー数　手数、自分の番号
		sb.append(String.format("%d %d %d\n", shapeLists.length
				,shapeLists[0].size(),index));
		for (int i=0; i < shapeLists.length;i++) {
			for (int t=0; t < shapeLists[i].size();t++) {
				sb.append(shapeLists[i].get(t));
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
