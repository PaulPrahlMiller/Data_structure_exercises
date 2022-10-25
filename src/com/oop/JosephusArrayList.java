package com.oop;

import java.util.ArrayList;

public class JosephusArrayList extends Josephus{

    int start;

    public JosephusArrayList(int players, int passes){
        super(players, passes, new ArrayList<>());
    }

    public int nextEliminated() {

        while(playerList.size() > 1){
            int nextPos = (start + passes) % playerList.size();
            removePlayer(nextPos);
            start = nextPos;
        }
        return playerList.get(0);
    }

    private void removePlayer(int index) {
        playerList.remove(index);
    }

}
