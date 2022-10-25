package com.oop;

import java.util.LinkedList;

public class JosephusLinkedListIterator extends Josephus{

    public JosephusLinkedListIterator(int players, int passes) {
        super(players, passes, new LinkedList<>());
    }

    public int nextEliminated() {

        while(playerList.size() > 1){
            int passCount = 0;
            while(passCount <= passes){
                if(!iterator.hasNext())
                    iterator = playerList.iterator();

                iterator.next();
                passCount++;
            }
            iterator.remove();
        }
        return playerList.get(0);
    }
}
