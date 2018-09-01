package test

import java.util.ArrayList
import java.util.Arrays

import javax.swing.RootPaneContainer

import game.controller.CommandLineController
import game.controller.group.ControllerGroup
import game.data.Data
import game.data.TextCommandData
import game.rps.RPS
import game.rps.controller.RPSRandom
import game.rps.data.RPSStateData
import game.rps.shape.RPSResult
import game.rps.shape.Shape

import org.junit.jupiter.api.*
import kotlin.test.assertEquals;

import game.rps.shape.RPSResult.*
import game.rps.shape.Shape.*
class GameTest{
    internal var rps: RPS = RPS(ControllerGroup( RPSRandom("test")));
    internal var clc: CommandLineController = CommandLineController()
    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {

    }

    @Test
    fun checkSHapeJudge() {
        assertEquals(ROCK.judge(ROCK), DRAW)
        assertEquals(ROCK.judge(PAPER), LOSE)
        assertEquals(ROCK.judge(SCISSOR), WIN)
        assertEquals(PAPER.judge(ROCK), WIN)
        assertEquals(PAPER.judge(PAPER), DRAW)
        assertEquals(PAPER.judge(SCISSOR), LOSE)
        assertEquals(SCISSOR.judge(ROCK), LOSE)
        assertEquals(SCISSOR.judge(PAPER), WIN)
        assertEquals(SCISSOR.judge(SCISSOR), DRAW)

    }

    @Test
    fun checkSHapeJudgeAll() {

        val results = Shape.judgeAll(SCISSOR, SCISSOR, SCISSOR, SCISSOR)
        //Arrays.stream(results).forEach(e->System.out.print(e+ ","));
        //System.out.println();

    }

//    @Test
//    fun checkText() {
//        val lists = arrayOf<List<Shape>>(
//                object : ArrayList<Shape>() {
//            init {
//                add(ROCK)
//                add(PAPER)
//                add(SCISSOR)
//            }
//        }
//                , object : ArrayList<Shape>() {
//            init {
//                add(ROCK)
//                add(ROCK)
//                add(ROCK)
//            }
//        }
//                ,object : ArrayList<Shape>() {
//            init {
//                add(SCISSOR)
//                add(PAPER)
//                add(SCISSOR)
//            }
//        })
//
//    val rs = RPSStateData(lists, 1)
//    println(rs)
//
//}


}
