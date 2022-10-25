package com.oop;

import java.util.Iterator;
import java.util.List;

abstract class Josephus {

    List<Integer> playerList;
    Iterator<Integer> iterator;
    int players;
    int passes;
    int listSize;
    int start;
    int current;

    Josephus(int players, int passes, List<Integer> dataStructure){
        this.players = players;
        this.passes = passes;
        this.playerList = dataStructure;
        initGame();
    }

    private void initGame(){
        playerList.clear();
        listSize = 0;
        start = 0;
        current = 0;
        for(int i = 1; i <= players; i++) {
            playerList.add(i);
            listSize++;
        }
        iterator = playerList.iterator();
    }

    public int main() {
        int winner = -1;
        if(!playerList.isEmpty())
            winner = nextEliminated();
        return winner;
    }

    abstract public int nextEliminated();
}
