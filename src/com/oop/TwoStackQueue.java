package com.oop;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class TwoStackQueue {

    ArrayDeque<Integer> stack1 = new ArrayDeque<>();
    ArrayDeque<Integer> stack2 = new ArrayDeque<>();
    private int maxSize;

    public TwoStackQueue(int size) {
        maxSize = size;
    }

    public int deQueue() throws Exception {
        if(stack1.isEmpty())
            throw new Exception("Underflow error: The queue is empty");

        while(stack1.size() > 1){
            stack2.push(stack1.pop());
        }

        int val = stack1.pop();

        while(stack2.size() > 0){
            stack1.push(stack2.pop());
        }

        return val;
    }

    public void enQueue(int val) throws Exception {
        // Add value to stack 1 directly if it is empty
        if(stack1.size() == maxSize)
            throw new Exception("Overflow error: The queue is full");

        stack1.push(val);
    }

    public static void main() {

        double start;
        double end;
        double runtime;
        TwoStackQueue queue;
        ArrayList<Integer> inputSizes = new ArrayList<>(Arrays.asList(10, 100, 1000, 10_000));

        try {

            for (int size : inputSizes) {
                queue = new TwoStackQueue(size);
                int i = 0;
                start = System.nanoTime();
                while (i < size) {
                    queue.enQueue(1);
                    i++;
                }

                while (i > 0) {
                    queue.deQueue();
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
                TwoStackQueue fullQueue = new TwoStackQueue(0);
                fullQueue.enQueue(1);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try{
                TwoStackQueue emptyQueue = new TwoStackQueue(0);
                emptyQueue.deQueue();
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        catch (StackOverflowError e){
            System.out.println("Recursion caused a stack overflow");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
