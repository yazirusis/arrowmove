package main

import game.Match
import game.controller.group.ControllerGroup
import game.rps.RPS
import game.rps.controller.RPSRandom
import java.util.*

fun main(args: Array<String>) {
    println("Hello")
    var num = 1
    if (args.size != 0) {
        num = Integer.parseInt(args[0])
    }
    val cg = ControllerGroup(
            RPSRandom("rand1"), RPSRandom("rand2"), RPSRandom("rand3"), RPSRandom("rand2"))//,new RPSRandom("rand4"),new RPSRandom("rand2"),new RPSRandom("rand2")
    //,new RPSRandom("rand2"),new RPSRandom("rand2"),new RPSRandom("rand2")
    val match = Match(RPS(cg))

    match.open()

    val sc = Scanner(System.`in`)
    while (true) {
        val s = sc.next()
        if (s.equals("r")) {
            match.next()
        } else if (s.equals("u")) {
            match.previous()
        } else if (s.equals("e")) {
            break
        }
        match.close()
    }
}