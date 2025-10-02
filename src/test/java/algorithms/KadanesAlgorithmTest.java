package algorithms;

import org.junit.jupiter.api.Test;
import metrics.PerformanceTracker;

import static org.junit.jupiter.api.Assertions.*;

public class KadanesAlgorithmTest {

    private final KadanesAlgorithm alg = new KadanesAlgorithm();
    private final PerformanceTracker tracker = new PerformanceTracker();

    @Test
    void testMaxSubarraySum() {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        KadanesAlgorithm.SubarrayResult result = alg.findMaxSubarraySum(nums, tracker);
        assertEquals(6, result.maxSum, "Maximum subarray sum should be 6");
        assertEquals(3, result.start, "Start index should be 3");
        assertEquals(6, result.end, "End index should be 6");
    }

    @Test
    void testEmptyArray() {
        int[] nums = {};
        assertThrows(IllegalArgumentException.class, () -> {
            alg.findMaxSubarraySum(nums, tracker);
        }, "Should throw IllegalArgumentException for empty array");
    }

    @Test
    void testSingleElement() {
        int[] nums = {5};
        KadanesAlgorithm.SubarrayResult result = alg.findMaxSubarraySum(nums, tracker);
        assertEquals(5, result.maxSum, "Single element should be the maximum sum");
        assertEquals(0, result.start, "Start index should be 0");
        assertEquals(0, result.end, "End index should be 0");
    }

    @Test
    void testAllNegative() {
        int[] nums = {-1, -2, -3, -4};
        KadanesAlgorithm.SubarrayResult result = alg.findMaxSubarraySum(nums, tracker);
        assertEquals(-1, result.maxSum, "Maximum sum should be the largest negative element");
        assertEquals(0, result.start, "Start index should be 0");
        assertEquals(0, result.end, "End index should be 0");
    }

    @Test
    void testDuplicates() {
        int[] nums = {1, 1, 1, -1, -1, 1};
        KadanesAlgorithm.SubarrayResult result = alg.findMaxSubarraySum(nums, tracker);
        assertEquals(3, result.maxSum, "Maximum sum should be 3 from consecutive duplicates");
        assertEquals(0, result.start, "Start index should be 0");
        assertEquals(2, result.end, "End index should be 2");
    }

    @Test
    void testSortedArray() {
        int[] nums = {1, 2, 3, 4, 5};
        KadanesAlgorithm.SubarrayResult result = alg.findMaxSubarraySum(nums, tracker);
        assertEquals(15, result.maxSum, "Maximum sum should be the entire array");
        assertEquals(0, result.start, "Start index should be 0");
        assertEquals(4, result.end, "End index should be 4");
    }

    @Test
    void testReverseSortedArray() {
        int[] nums = {5, 4, 3, 2, 1};
        KadanesAlgorithm.SubarrayResult result = alg.findMaxSubarraySum(nums, tracker);
        assertEquals(15, result.maxSum, "Maximum sum should be the entire array");
        assertEquals(0, result.start, "Start index should be 0");
        assertEquals(4, result.end, "End index should be 4");
    }
}