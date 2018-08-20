package main;

import java.util.Scanner;

import game.Match;
import game.controller.group.ControllerGroup;
import game.rps.RPS;
import game.rps.controller.RPSRandom;

public class MatchPlay {
	public static void main(String[] args) {
		int num = 1;
		if(args.length != 0) {
			num = Integer.parseInt(args[0]);
		}
		ControllerGroup cg = new ControllerGroup(
				new RPSRandom("rand1"),new RPSRandom("rand2"),new RPSRandom("rand3")
				//,new RPSRandom("rand4"),new RPSRandom("rand2"),new RPSRandom("rand2")
				//,new RPSRandom("rand2"),new RPSRandom("rand2"),new RPSRandom("rand2")
				,new RPSRandom("rand2"));
		Match match = new Match(new RPS(cg));

		match.open();;

		Scanner sc = new Scanner(System.in);
		while(true) {
			String s = sc.next();
			if (s.equals("r")) {
				match.next();
			}else if (s.equals("u") ){
				match.previous();
			}else if (s.equals("e")){
				break;
			}
			match.close();
		}

	}

}
