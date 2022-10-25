package com.oop;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    static int setPasses(int inputSize, int index) {
        switch (index) {
            case 1 -> {
                return inputSize / 2;
            }
            case 2 -> {
                return inputSize - 1;
            }
            case 3 -> {
                return (int) (Math.random() * (inputSize - 1));
            }
            default -> {
                return 1;
            }
        }
    }

    private static void printTableRow(String[] col) {
        System.out.printf("%-25s%-25s%-30s%-25s%-30s%n", col[0], col[1], col[2], col[3], col[4]);
    }

    public static void main(String[] args) {

        String filename;

        if(args.length > 0) {
            filename = args[0];
            PrintStream output = null;
            try {
                output = new PrintStream(new BufferedOutputStream(new FileOutputStream(filename)), true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.setOut(output);
        }

//////// Task 1 ////////////

        System.out.println(
                """
                        Task 1
                        ====================================
                        """
        );
        Compiler.main();

//////// Task 2 ////////////

        System.out.println(
                """
                        Task 2
                        ===================================="""
        );
        System.out.println("\nTwo Stack Queue");
        TwoStackQueue.main();
        // b
        System.out.println("\nOne Stack Queue");
        OneStackQueue.main();
        // c
        System.out.println("\nTwo Queue Stack");
        TwoQueueStack.main();
        // d
        System.out.println("\nOne Queue Stack");
        OneQueueStack.main();

//////// Task 3 ////////////////
        System.out.println(
                """
                        Task 3
                        ===================================="""
        );
        CustomLinkedList.main();

////// Task 4 ////////////////

        ArrayList<Integer> inputSize = new ArrayList<>(Arrays.asList(
                100, 1000, 10_000, 25_000, 50_000, 75_000, 100_000
        ));
        ArrayList<String> dataStructure = new ArrayList<>(Arrays.asList(
                "ArrayList", "ArrayListIterator", "LinkedList", "LinkedListIterator"));
        double start;
        double end;
        double runTime = 0;

        System.out.println(
                """
                        Task 4
                        ===================================="""
        );

        // Loop each size
        for(int size : inputSize) {

            // Loop each size -> M-value
            for(int i = 0; i <= 3; i++) {

                int passes = setPasses(size, i);

                StringBuilder times = new StringBuilder();
                StringBuilder winners = new StringBuilder();
                times.append(" ");
                winners.append("Winner");

                // Loop each size -> M-value -> data structure
                for (String d : dataStructure) {
                    int winner = -1;
                    TimeKeeper tk = new TimeKeeper(10);

                    // Loop each size -> M-value -> data structure for a specified amount of runs
                    while (tk.shouldRun()) {
                        switch (d) {
                            case "ArrayList" -> {
                                JosephusArrayList j = new JosephusArrayList(size, passes);
                                start = System.nanoTime();
                                winner = j.main();
                                end = System.nanoTime();
                                runTime = (end - start);
                            }
                            case "ArrayListIterator" -> {
                                JosephusArrayIterator j2 = new JosephusArrayIterator(size, passes);
                                start = System.nanoTime();
                                winner = j2.main();
                                end = System.nanoTime();
                                runTime = (end - start);
                            }
                            case "LinkedList" -> {
                                JosephusLinkedList j3 = new JosephusLinkedList(size, passes);
                                start = System.nanoTime();
                                winner = j3.main();
                                end = System.nanoTime();
                                runTime = (end - start);
                            }
                            case "LinkedListIterator" -> {
                                JosephusLinkedListIterator j4 = new JosephusLinkedListIterator(size, passes);
                                start = System.nanoTime();
                                winner = j4.main();
                                end = System.nanoTime();
                                runTime = (end - start);
                            }
                        }
                        tk.update(runTime);
                    }
                    times.append(String.format("-%.2f ms", tk.getAverageMilli()));
                    winners.append(String.format("-Player %d", winner));
                }
                System.out.println("-".repeat(125));
                printTableRow("Input sizes-ArrayList-ArrayList iterator-LinkedList-LinkedList iterator".split("-"));
                printTableRow(String.format("N = %d- - - - ", size).split("-"));
                printTableRow(String.format("M = %d- - - - ", passes).split("-"));
                printTableRow(times.toString().split("-"));
                printTableRow(winners.toString().split("-"));
                System.out.println("-".repeat(125));
                System.out.println("\n");

            }
        }
    }
}
