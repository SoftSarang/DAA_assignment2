package metrics;

import java.io.FileWriter;
import java.io.IOException;

public class PerformanceTracker {
    private long comparisons = 0;
    private long swaps = 0;
    private long accesses = 0;
    private long allocations = 0;
    private long startTime = 0;

    /**
     * Starts the performance tracking timer.
     */
    public void start() {
        startTime = System.nanoTime();
    }

    /**
     * Increments the comparison counter.
     */
    public void incrementComparison() {
        comparisons++;
    }

    /**
     * Increments the swap counter.
     */
    public void incrementSwap() {
        swaps++;
    }

    /**
     * Increments the array access counter.
     */
    public void incrementAccess() {
        accesses++;
    }

    /**
     * Increments the memory allocation counter.
     */
    public void incrementAllocation() {
        allocations++;
    }

    /**
     * Writes collected metrics to a CSV file.
     * @param filePath Path to the CSV file.
     * @param n Input size for the benchmark.
     * @param algorithm Name of the algorithm.
     * @throws IOException If file writing fails.
     */
    public void writeToCSV(String filePath, int n, String algorithm) throws IOException {
        long timeNs = System.nanoTime() - startTime;
        try (FileWriter writer = new FileWriter(filePath, true)) {
            if (writer == null) {
                throw new IOException("Failed to initialize FileWriter");
            }
            writer.append(String.format("%d,%d,%d,%d,%d,%d,%s\n", n, timeNs, comparisons, swaps, accesses, allocations, algorithm));
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
            throw e; // Re-throw to allow caller to handle
        }
    }

    /**
     * Resets all performance counters and timer to initial state.
     */
    public void reset() {
        comparisons = 0;
        swaps = 0;
        accesses = 0;
        allocations = 0;
        startTime = 0;
    }
}