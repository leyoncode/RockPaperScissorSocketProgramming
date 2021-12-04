package com.leyon;

import java.util.Random;
import java.lang.String;

public class RockPaperScissor {

    private Random random = new Random();
    private final String[] moves = {"rock","paper","scissor"};

    public RockPaperScissor() {
        //ctor
    }

    public String getCpuInput() {
        int choiceIndex = random.nextInt(3); //generate numbers randomly from 0-2 to use as index in moves array
        return moves[choiceIndex];
    }

    //return -1 for cpu, 0 for draw, and 1 for player win
    public int roundResult(String playerInput, String cpuInput) {
        int playerChoice = getMoveIndex(playerInput);
        int cpuChoice = getMoveIndex(cpuInput);

        if (playerChoice == cpuChoice) return 0; //draw
        else if (playerChoice == 0 && cpuChoice == 1) return -1; //cpu win. rock<paper
        else if (playerChoice == 0 && cpuChoice == 2) return 1; //player win. rock>scissor
        else if (playerChoice == 1 && cpuChoice == 0) return 1; //player win. paper>rock
        else if (playerChoice == 1 && cpuChoice == 2) return -1; //cpu win. paper<scissor
        else if (playerChoice == 2 && cpuChoice == 0) return -1; //cpu win. scissor<rock
        else if (playerChoice == 2 && cpuChoice == 1) return 1; //player win. scissor>paper

        return 0;
    }

    private int getMoveIndex(String input) {
        for (int i = 0; i < moves.length; i++) {
            //if (input.compareToIgnoreCase(moves[i]) == 0) {
            if (input.charAt(0) == moves[i].charAt(0)) { // to make avoid errors from spelling mistake
                return i;
            }
        }
        return 0; //default to rock/index 0 of moves in case of bad input
    }

    //pass result from roundResult() and get proper string
    public String getWinnerText(int gameResult) {
        if (gameResult == 1) return "Player won!";
        else if (gameResult == -1) return "Cpu won!";
        else return "Game is a Draw";
    }
}