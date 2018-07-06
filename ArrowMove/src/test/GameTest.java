package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.RootPaneContainer;

import game.controller.CommandLineController;
import game.data.Data;
import game.data.TextCommandData;
import game.rps.RPS;
import game.rps.data.RPSStateData;
import game.rps.shape.RPSResult;
import game.rps.shape.Shape;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static game.rps.shape.RPSResult.*;
import static game.rps.shape.Shape.*;
@RunWith(JUnit4.class)
public class GameTest extends TestCase {
	RPS rps;
	CommandLineController clc;
	@Before
	public void setUp() throws Exception {

		rps = new RPS(null);
		clc = new CommandLineController();
	}

	@Test
	public void checkSHapeJudge() {
		assertEquals(ROCK.judge(ROCK),DRAW);
		assertEquals(ROCK.judge(PAPER),LOSE);
		assertEquals(ROCK.judge(SCISSOR),WIN);
		assertEquals(PAPER.judge(ROCK),WIN);
		assertEquals(PAPER.judge(PAPER),DRAW);
		assertEquals(PAPER.judge(SCISSOR),LOSE);
		assertEquals(SCISSOR.judge(ROCK),LOSE);
		assertEquals(SCISSOR.judge(PAPER),WIN);
		assertEquals(SCISSOR.judge(SCISSOR),DRAW);
		
	}
	@Test
	public void checkSHapeJudgeAll() {
		
		RPSResult[] results = judgeAll(SCISSOR,SCISSOR,SCISSOR,SCISSOR);
		//Arrays.stream(results).forEach(e->System.out.print(e+ ","));
		//System.out.println();
		
	}
	@Test
	public void checkText() {
		List<Shape>[] lists = new List[3];
		lists[0] = new ArrayList<Shape>(){{add(ROCK);add(PAPER);add(SCISSOR);}};
		lists[1] = new ArrayList<Shape>(){{add(ROCK);add(ROCK);add(ROCK);}};
		lists[2] = new ArrayList<Shape>(){{add(SCISSOR);add(PAPER);add(SCISSOR);}};

		RPSStateData rs = new RPSStateData(lists, 1);
		System.out.println(rs);
		
	}
	

}
