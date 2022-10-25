package com.oop;

public class TimeKeeper {
    /**
     * Usage:
     *
     * Basic constructor
     * timekeeper = new Timekeeper(); -> returns a new timekeeper object that will run for 1 iteration.
     *
     * params constructor
     * @param x -> The number of iterations to record
     * timekeeper = new Timekeeper(x); -> returns a new timekeeper object that will run for 10 iterations.
     *
     * Calculate the runtime of the program and then pass in the time in nanoseconds to the update method.
     *
     *          double start = System.nanotime();
     *          // Execute the program
     *          double runtime = System.nanotime() - start;
     *
     *          timekeeper.update(runtime);
     *
     * Get best, worst and average runtime with getter methods.
     * */

    private final double one_million = 1_000_000.0;
    private Double worst_case = 0.0;
    private Double best_case = 0.0;
    private Double time_total = 0.0;
    private int found_count = 0;
    private int run_count = 0;
    private final int iterations;

    public TimeKeeper(int iterations) {
        this.iterations = iterations;
    }

    public TimeKeeper(){
        this(1);
    }

    public boolean shouldRun() {
        return run_count < iterations;
    }

    public void update(double time){

        if(run_count == 0) {
            best_case = time;
            worst_case = time;
        }

        this.time_total += time;
        if(time < best_case)
            best_case = time;
        if(time > worst_case)
            this.worst_case = time;

        run_count++;
    }

    double getBestMilli(){
        return best_case / one_million;
    }

    double getWorstMilli(){
        return worst_case / one_million;
    }

    double getAverageMilli(){
        return time_total / one_million / (double) run_count;
    }

    double getAverageNano(){
        return time_total / run_count;
    }

    int getFound() { return found_count; }

    void setFound() { found_count++; }

    int getRunCount() { return run_count; }
}
