package cli;

import algorithms.KadanesAlgorithm;
import metrics.PerformanceTracker;
import java.io.IOException;

public class BenchmarkRunner {
    public static void main(String[] args) {
        // Default sizes if no argument provided or if parsing fails
        int[] sizes = {100, 1000, 10000, 100000};
        if (args.length > 0) {
            sizes = new int[args.length];
            for (int i = 0; i < args.length; i++) {
                try {
                    sizes[i] = Integer.parseInt(args[i]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid size argument at index " + i + ": '" + args[i] + "'. Using default sizes for this position.");
                    sizes[i] = (i < 4) ? new int[]{100, 1000, 10000, 100000}[i] : 100; // Fallback to default or 100
                }
            }
        }

        KadanesAlgorithm alg = new KadanesAlgorithm();
        for (int size : sizes) {
            int[] nums = new int[size];
            // Generate random numbers with negatives
            for (int i = 0; i < size; i++) {
                nums[i] = (int) (Math.random() * 100 - 50); // Random with negatives
            }
            PerformanceTracker tracker = new PerformanceTracker();
            tracker.start();
            alg.findMaxSubarraySum(nums, tracker);
            try {
                tracker.writeToCSV("performance.csv", size, "Kadane's");
            } catch (IOException e) {
                System.err.println("Failed to write to performance.csv for size " + size + ": " + e.getMessage());
            }
        }
        System.out.println("Benchmark completed. Results written to performance.csv.");
    }
}