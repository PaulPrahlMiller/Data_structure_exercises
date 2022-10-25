package com.oop;

import java.util.ArrayList;

public class JosephusArrayIterator extends Josephus {

    public JosephusArrayIterator(int players, int passes){
        super(players, passes, new ArrayList<>());
    }

    public int nextEliminated() {
        while(playerList.size() > 1){
            int passCount = 0;
            while(passCount++ <= passes){

                if(!iterator.hasNext())
                    iterator = playerList.iterator();

                iterator.next();
            }
            iterator.remove();
        }
        return playerList.get(0);
    }
}
