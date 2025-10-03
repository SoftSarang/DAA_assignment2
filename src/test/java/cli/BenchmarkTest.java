package cli;

import algorithms.KadanesAlgorithm;
import metrics.PerformanceTracker;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
public class BenchmarkTest {

    @Param({"100", "1000", "10000", "100000"})
    private int size;

    private int[] nums;

    @Setup
    public void setup() {
        nums = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            nums[i] = rand.nextInt(100) - 50; // Random with negatives
        }
    }

    @Benchmark
    public void benchmarkKadane(Blackhole blackhole) {
        KadanesAlgorithm alg = new KadanesAlgorithm();
        KadanesAlgorithm.SubarrayResult result = alg.findMaxSubarraySum(nums, new PerformanceTracker());
        blackhole.consume(result);
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}