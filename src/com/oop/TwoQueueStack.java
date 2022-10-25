package com.oop;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class TwoQueueStack {

    ArrayDeque<Integer> queue1 = new ArrayDeque<>();
    ArrayDeque<Integer> queue2 = new ArrayDeque<>();
    int maxSize;

    public TwoQueueStack(int size) {
        maxSize = size;
    }

    public int pop() throws Exception {

        // Handle underflow Error when stack is empty.
        if(queue1.isEmpty()){
            throw new Exception("Underflow error: The stack is empty");
        }

        while(queue1.size() > 1) {
            queue2.add(queue1.remove());
        }

        int topStack = queue1.remove();

        while(!queue2.isEmpty()){
            queue1.add(queue2.remove());
        }

        return topStack;
    }

    public void push(int val){

        if(queue1.size() == maxSize){
            throw new StackOverflowError("Overflow error: The stack is full");
        }

        queue1.add(val);

    }

    public static void main() {
        double start;
        double end;
        double runtime;
        ArrayList<Integer> inputSizes = new ArrayList<>(Arrays.asList(10, 100, 1000, 10_000));
        TwoQueueStack stack;

        try{

            for(int size : inputSizes){
                stack = new TwoQueueStack(size);
                int i = 0;
                start = System.nanoTime();
                while(i < size){
                    stack.push(1);
                    i++;
                }

                while(i > 0){
                    stack.pop();
                    i--;
                }
                end = System.nanoTime();
                runtime = (end - start) / 1_000_000.0;
                System.out.printf("""
                    Input size: %d
                    Runtime: %f ms
                    """, size, runtime);

            }

            System.out.println("""
                --------------
                Error handling
                --------------""");
            try{
                TwoQueueStack fullStack = new TwoQueueStack(0);
                fullStack.push(6);
            } catch(StackOverflowError e) {
                System.out.println(e.getMessage());
            }
            // Underflow error when popping from an empty stack
            try{
                TwoQueueStack emptyStack = new TwoQueueStack(0);
                emptyStack.pop();
            } catch(NoSuchElementException e){
                System.out.println(e.getMessage());
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

