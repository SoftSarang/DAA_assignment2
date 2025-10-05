package algorithms;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import metrics.PerformanceTracker;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class KadanesAlgorithmTest {
    private KadanesAlgorithm alg;
    private PerformanceTracker tracker;

    @BeforeEach
    void setUp() {
        alg = new KadanesAlgorithm();
        tracker = new PerformanceTracker();
    }

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
    void testSingleElementPositive() {
        int[] nums = {5};
        KadanesAlgorithm.SubarrayResult res = alg.findMaxSubarraySum(nums, tracker);
        assertEquals(5, res.maxSum);
        assertEquals(0, res.start);
        assertEquals(0, res.end);
    }

    @Test
    void testSingleElementNegative() {
        int[] nums = {-5};
        KadanesAlgorithm.SubarrayResult res = alg.findMaxSubarraySum(nums, tracker);
        assertEquals(-5, res.maxSum);
        assertEquals(0, res.start);
        assertEquals(0, res.end);
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
    void testAllPositive() {
        int[] nums = {1, 2, 3};
        KadanesAlgorithm.SubarrayResult res = alg.findMaxSubarraySum(nums, tracker);
        assertEquals(6, res.maxSum);
        assertEquals(0, res.start);
        assertEquals(2, res.end);
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

    @Test
    void testAllZeros() {
        int[] nums = {0, 0, 0};
        KadanesAlgorithm.SubarrayResult res = alg.findMaxSubarraySum(nums, tracker);
        assertEquals(0, res.maxSum);
        assertEquals(0, res.start);
        assertEquals(0, res.end);
    }

    @Test
    void testNearlySortedData() {
        int[] nums = {1, 2, 4, 3, 5, 6, 8, 7, 9, 10};
        KadanesAlgorithm alg = new KadanesAlgorithm();
        PerformanceTracker tracker = new PerformanceTracker();
        KadanesAlgorithm.SubarrayResult result = alg.findMaxSubarraySum(nums, tracker);

        assertEquals(55, result.maxSum, "Maximum sum should be 55 for the entire nearly-sorted array");
        assertEquals(0, result.start, "Start index should be 0");
        assertEquals(9, result.end, "End index should be 9");
    }

    @Test
    void testMemoryProfiling() {
        int[] nums = new int[10000];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = (int) (Math.random() * 100) - 50;
        }
        KadanesAlgorithm alg = new KadanesAlgorithm();
        PerformanceTracker tracker = new PerformanceTracker();

        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage initialUsage = memoryBean.getHeapMemoryUsage();
        long initialHeapUsage = initialUsage.getUsed();
        long initialGcCount = 0, initialGcTime = 0;
        for (GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            initialGcCount += gcBean.getCollectionCount();
            initialGcTime += gcBean.getCollectionTime();
        }

        KadanesAlgorithm.SubarrayResult result = alg.findMaxSubarraySum(nums, tracker);


        MemoryUsage finalUsage = memoryBean.getHeapMemoryUsage();
        long finalHeapUsage = finalUsage.getUsed();
        long finalGcCount = 0, finalGcTime = 0;
        for (GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            finalGcCount += gcBean.getCollectionCount();
            finalGcTime += gcBean.getCollectionTime();
        }
        long gcCollectionCount = finalGcCount - initialGcCount;
        long gcCollectionTime = finalGcTime - initialGcTime;

        System.out.println("Memory Profiling Results:");
        System.out.println("Initial Heap Usage: " + initialHeapUsage + " bytes");
        System.out.println("Final Heap Usage: " + finalHeapUsage + " bytes");
        System.out.println("Heap Usage Difference: " + (finalHeapUsage - initialHeapUsage) + " bytes");
        System.out.println("GC Collection Count: " + gcCollectionCount);
        System.out.println("GC Collection Time: " + gcCollectionTime + " ms");
        System.out.println("------------------------");

        assertTrue(finalHeapUsage >= initialHeapUsage, "Final heap usage should be greater than or equal to initial usage");
        assertTrue(gcCollectionCount >= 0, "GC collection count should be non-negative");
        assertTrue(gcCollectionTime >= 0, "GC collection time should be non-negative");
    }
}