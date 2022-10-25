package com.oop;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class OneQueueStack {

    ArrayDeque<Integer> queue = new ArrayDeque<>();
    int max_size;

    public OneQueueStack(int size) {
        max_size = size;
    }

    public int pop() throws Exception {

        if(queue.size() == 0){
            throw new NoSuchElementException("Underflow Error: The stack is empty");
        }

        if(queue.size() == 1){
            return queue.remove();
        }

        int removedVal = queue.remove();

        int returnValue = pop();

        queue.push(removedVal);

        return returnValue;

    }

    public void push(int val) {
        if(queue.size() == max_size){
            throw new StackOverflowError("Overflow error: The stack is full");
        }
        queue.add(val);
    }

    public static void main() {
        double start;
        double end;
        double runtime;
        ArrayList<Integer> inputSizes = new ArrayList<>(Arrays.asList(10, 100, 1000, 10_000));


        try{
            OneQueueStack stack;

            for(int size : inputSizes){
                stack = new OneQueueStack(size);
                start = System.nanoTime();
                while(stack.queue.size() < size - 1)
                    stack.push(1);
                while(stack.queue.size() > 0)
                    stack.pop();
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
                OneQueueStack fullStack = new OneQueueStack(0);
                fullStack.push(6);
            } catch(StackOverflowError e) {
                System.out.println(e.getMessage());
            }
            // Underflow error when popping from an empty stack
            try{
                OneQueueStack emptyStack = new OneQueueStack(0);
                emptyStack.pop();
            } catch(NoSuchElementException e){
                System.out.println(e.getMessage());
            }

        } catch(StackOverflowError e){
            System.out.println("Recursion caused a stack overflow");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
