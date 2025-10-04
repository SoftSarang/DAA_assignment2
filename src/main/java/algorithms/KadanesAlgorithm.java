package algorithms;

import metrics.PerformanceTracker;

/**
 * Implements Kadane's Algorithm for maximum subarray sum with position tracking.
 * Time Complexity: Θ(n) for best, worst, average cases.
 * Space Complexity: O(1) auxiliary.
 * Handles edges: all-negative (returns max element), single element, empty/null (throws exception).
 * Optimizations: Extends subarray for non-negative sums, including zeros, to maximize length.
 */
public class KadanesAlgorithm {
    public static class SubarrayResult {
        public long maxSum;
        int start;
        int end;

        public SubarrayResult(long maxSum, int start, int end) {
            this.maxSum = maxSum;
            this.start = start;
            this.end = end;
        }
    }

    /**
     * Finds the maximum contiguous subarray sum using Kadane's Algorithm.
     * Tracks start and end positions. Handles all-negative cases by returning the largest element.
     * @param nums Input array (non-null, non-empty)
     * @param tracker Performance metrics collector
     * @return SubarrayResult with max sum and positions
     * @throws IllegalArgumentException if nums is null or empty
     */
    public SubarrayResult findMaxSubarraySum(int[] nums, PerformanceTracker tracker) {
        if (nums == null) throw new IllegalArgumentException("Input array cannot be null");
        if (nums.length == 0) throw new IllegalArgumentException("Input array cannot be empty");


        if (nums.length == 1) {
            tracker.incrementAccess();
            tracker.incrementAllocation();
            return new SubarrayResult(nums[0], 0, 0);
        }

        // Standard Kadane's
        long maxSum = nums[0];
        long currentSum = nums[0];
        int start = 0;
        int currentStart = 0;
        int end = 0;
        long maxElement = nums[0];
        int maxElementIndex = 0;

        tracker.incrementAccess();

        for (int i = 1; i < nums.length; i++) {
            tracker.incrementAccess();
            tracker.incrementComparison();
            if (currentSum <= 0) {
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

        if (maxSum < 0) {
            tracker.incrementAllocation();
            return new SubarrayResult(maxElement, maxElementIndex, maxElementIndex);
        }
        tracker.incrementAllocation();
        return new SubarrayResult(maxSum, start, end);
    }
}