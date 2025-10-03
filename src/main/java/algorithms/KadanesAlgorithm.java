package algorithms;

import metrics.PerformanceTracker;

public class KadanesAlgorithm {
    public static class SubarrayResult {
        public int maxSum;
        int start;
        int end;

        public SubarrayResult(int maxSum, int start, int end) {
            this.maxSum = maxSum;
            this.start = start;
            this.end = end;
        }
    }

    public SubarrayResult findMaxSubarraySum(int[] nums, PerformanceTracker tracker) {
        if (nums == null) throw new IllegalArgumentException("Input array cannot be null");
        if (nums.length == 0) throw new IllegalArgumentException("Input array cannot be empty");

        if (nums.length == 1) {
            tracker.incrementAccess();
            tracker.incrementAllocation();
            return new SubarrayResult(nums[0], 0, 0);
        }

        int maxSum = nums[0];
        int currentSum = nums[0];
        int start = 0;
        int currentStart = 0;
        int end = 0;
        int maxElement = nums[0];
        int maxElementIndex = 0;

        tracker.incrementAccess();

        for (int i = 1; i < nums.length; i++) {
            tracker.incrementAccess();
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