package cli;

import algorithms.KadanesAlgorithm;
import metrics.PerformanceTracker;
import java.io.IOException;
import java.util.Random;

public class BenchmarkRunner {
    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 100000};
        if (args.length > 0) {
            sizes = new int[args.length];
            for (int i = 0; i < args.length; i++) {
                try {
                    sizes[i] = Integer.parseInt(args[i]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid size argument at index " + i + ": '" + args[i] + "'. Using default sizes for this position.");
                    sizes[i] = (i < 4) ? new int[]{100, 1000, 10000, 100000}[i] : 100;
                }
            }
        }

        KadanesAlgorithm alg = new KadanesAlgorithm();
        Random rand = new Random();

        for (int size : sizes) {
            int[] nums = new int[size];
            for (int i = 0; i < size; i++) {
                nums[i] = rand.nextInt(100) - 50; // Consistent random generation
            }

            // Multiple warmups
            for (int i = 0; i < 5; i++) { // 5 warmups
                PerformanceTracker warmupTracker = new PerformanceTracker();
                alg.findMaxSubarraySum(nums, warmupTracker);
            }

            // Measurement
            PerformanceTracker tracker = new PerformanceTracker();
            tracker.start();
            KadanesAlgorithm.SubarrayResult result = alg.findMaxSubarraySum(nums, tracker);
            tracker.stop();
            try {
                tracker.writeToCSV("results.csv", size);
            } catch (IOException e) {
                System.err.println("Failed to write to results.csv for size " + size + ": " + e.getMessage());
            }
        }
        System.out.println("Benchmark completed. Results written to results.csv.");
    }
}