package algorithms;

import metrics.PerformanceTracker;

public class KadanesAlgorithm {
    /**
     * Represents the result of the maximum subarray sum with start and end indices.
     */
    public static class SubarrayResult {
        int maxSum;
        int start;
        int end;

        public SubarrayResult(int maxSum, int start, int end) {
            this.maxSum = maxSum;
            this.start = start;
            this.end = end;
        }
    }

    /**
     * Finds the maximum subarray sum with start and end indices using Kadane's algorithm.
     * @param nums The input array of integers.
     * @param tracker The performance tracker for metrics collection.
     * @return SubarrayResult containing the maximum sum and its boundaries.
     * @throws IllegalArgumentException if the array is null or empty.
     */
    public SubarrayResult findMaxSubarraySum(int[] nums, PerformanceTracker tracker) {
        // Input validation
        if (nums == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }
        if (nums.length == 0) {
            throw new IllegalArgumentException("Input array cannot be empty");
        }

        int maxSum = nums[0];
        int currentSum = nums[0];
        int start = 0;
        int currentStart = 0;
        int end = 0;
        int maxElement = nums[0];
        int maxElementIndex = 0;

        tracker.incrementAccess(); // Initial array access

        try {
            for (int i = 1; i < nums.length; i++) {
                tracker.incrementAccess(); // Access to nums[i]
                tracker.incrementComparison();
                if (currentSum < 0) {
                    currentSum = nums[i];
                    currentStart = i;
                } else {
                    currentSum += nums[i];
                }
                tracker.incrementComparison();
                if (maxSum < currentSum) {
                    maxSum = currentSum;
                    start = currentStart;
                    end = i;
                }
                tracker.incrementComparison();
                if (maxElement < nums[i]) {
                    maxElement = nums[i];
                    maxElementIndex = i;
                }
            }
            // Handle case where all elements are negative
            if (maxSum < 0) {
                tracker.incrementAllocation();
                return new SubarrayResult(maxElement, maxElementIndex, maxElementIndex);
            }
            tracker.incrementAllocation();
            return new SubarrayResult(maxSum, start, end);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalStateException("Array index out of bounds during processing: " + e.getMessage(), e);
        }
    }
}