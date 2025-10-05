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

        String[] dataTypes = {"random", "sorted", "reverse-sorted", "nearly-sorted"};
        for (int size : sizes) {
            for (String dataType : dataTypes) {
                int[] nums = generateArray(size, dataType, rand);

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
                    tracker.writeToCSV("results.csv", size, dataType); // Pass dataType to CSV
                } catch (IOException e) {
                    System.err.println("Failed to write to results.csv for size " + size + " and type " + dataType + ": " + e.getMessage());
                }
            }
        }
        System.out.println("Benchmark completed. Results written to results.csv.");
    }

    private static int[] generateArray(int size, String dataType, Random rand) {
        int[] nums = new int[size];
        switch (dataType) {
            case "random":
                for (int i = 0; i < size; i++) {
                    nums[i] = rand.nextInt(100) - 50; // Random between -50 and 49
                }
                break;
            case "sorted":
                for (int i = 0; i < size; i++) {
                    nums[i] = i - 50; // Ascending, offset by -50 for negative/positive mix
                }
                break;
            case "reverse-sorted":
                for (int i = 0; i < size; i++) {
                    nums[i] = size - 1 - i - 50; // Descending, offset by -50
                }
                break;
            case "nearly-sorted":
                for (int i = 0; i < size; i++) {
                    nums[i] = i - 50; // Base sorted
                    if (i > 0 && rand.nextDouble() < 0.1) { // 10% chance to swap with previous
                        int temp = nums[i];
                        nums[i] = nums[i - 1];
                        nums[i - 1] = temp;
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown data type: " + dataType);
        }
        return nums;
    }
}