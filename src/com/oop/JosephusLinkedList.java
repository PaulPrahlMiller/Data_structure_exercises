package com.oop;

import java.util.Iterator;

public class JosephusLinkedList {

    CustomLinkedList<Integer> playerList = new CustomLinkedList<>();
    Iterator<Integer> iterator;
    int players;
    int passes;
    int listSize;
    int start;

    public JosephusLinkedList(int players, int passes){
        this.players = players;
        this.passes = passes;
        initPlayers();
    }

    private void initPlayers() {
        playerList.clear();
        listSize = 0;
        for(int i = 1; i <= players; i++) {
            playerList.add(i);
            listSize++;
        }
        iterator = playerList.iterator();
    }

    public int nextEliminated(){
        while(playerList.size() > 1){
            start = (start + passes) % playerList.size();
            removePlayer(start);
        }
        return playerList.get(0);
    }

    private void removePlayer(int index){
        playerList.remove(index);
    }

    public int main(){
        int winner = -1;
        if(!playerList.isEmpty())
            winner = nextEliminated();
        return winner;
    }
}
