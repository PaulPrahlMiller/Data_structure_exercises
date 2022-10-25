package com.oop;

import java.util.*;

/* Source
* https://stackoverflow.com/questions/12470626/how-can-one-implement-a-queue-with-only-a-stack-implementation/12470690
*/

public class OneStackQueue {

    ArrayDeque<Integer> stack = new ArrayDeque<>();
    private final int MAX_SIZE;

    public OneStackQueue(int size) {
        MAX_SIZE = size;
    }

    public void enQueue(int val) {
        if(stack.size() == MAX_SIZE)
            throw new IllegalStateException("Overflow Error: The queue is full");
        stack.push(val);
    }

    public int deQueue() {

        if(stack.isEmpty()){
            throw new IllegalStateException("Underflow Error: The queue is empty");
        }

        // Returns the oldest (bottom) item in the stack
        // Base case
        if(stack.size() == 1){
            return stack.pop();
        }

        // Store the value that is popped from the stack. So it can be
        // placed back onto the stack recursively when the bottom item
        // has been returned.
        int valueRemoved = stack.pop();

        // Recursively call the dequeue method and store the return value.
        int returnValue = deQueue();

        // After base case has returned, The following statement will run.

        // Add the items back to the stack, This will begin with the item that
        // was originally second from the bottom. The bottom item was popped
        // from the stack when the base case was met.
        stack.push(valueRemoved);

        return returnValue;
    }

    public static void main() {

        double start;
        double end;
        double runtime;
        OneStackQueue queue;
        ArrayList<Integer> inputSizes = new ArrayList<>(Arrays.asList(10, 100, 1000, 10_000));

        try {

            for (int size : inputSizes) {
                queue = new OneStackQueue(size);
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
            // Overflow error when adding an item to a queue that is full
            try{
                OneStackQueue fullQueue = new OneStackQueue(0);
                fullQueue.enQueue(1);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }

            // Underflow error when attempting to remove an item from an empty queue
            try{
                OneStackQueue emptyQueue = new OneStackQueue(0);
                emptyQueue.deQueue();
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        catch (StackOverflowError e){
            System.out.println("Recursion caused a stack overflow");
        }
    }

}
