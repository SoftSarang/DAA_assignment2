# Assignment 2

## Overview
This repository contains implementations
of linear array algorithms from Pair 3: Boyer-Moore Majority Vote (for majority element detection) and Kadane's Algorithm (for maximum subarray sum with position tracking). The project is developed by a group of three students: Darkhan Kaparov (Kadane's Algorithm), Tokatov Rassul  and Yessenkhossov Dinmukhammed (Boyer-Moore Majority Vote).

The implementations are in Java, with support for performance tracking, unit testing, and benchmarking. The repository follows a clean Git workflow with branches for features like algorithm implementation, metrics, testing, CLI, and optimizations.

Group Repositories:

- Darkhan Kaparov (Kadane's): https://github.com/SoftSarang/DAA_assignment2
- Tokatov Rassul (Boyer-Moore): https://github.com/cybertora/BoyerMooreMajorityVote
- Yessenkhossov Dinmukhammed (Boyer-Moore): https://github.com/Swoksaar0/DAA_2nd_Assignment

## Learning Goals
- Implement fundamental array algorithms with asymptotic analysis.
- Apply Big-O, Big-Theta, Big-Omega notations for best/worst/average cases.
- Conduct peer code review on efficiency and optimizations.
- Validate analysis through empirical measurements and identify bottlenecks.
- Communicate findings via reports and Git workflow.

## Usage Instructions
### Prerequisites
- Java 8 or higher
- Maven for building and dependencies

### Building the Project
1. Clone the repository:
   ```
   git clone https://github.com/SoftSarang/DAA_assignment2.git
   git clone https://github.com/cybertora/BoyerMooreMajorityVote.git
   git clone https://github.com/Swoksaar0/DAA_2nd_Assignment.git

   ```
2. Build with Maven:
   ```
   mvn clean install
   ```

### Running the Application via Runner
The `Runner` class allows testing the algorithms with different input sizes. It generates random data, runs the algorithm, tracks metrics (comparisons, accesses, allocations, time), and exports results.

Example for Kadane's Algorithm:

- Arguments: Custom input sizes (defaults to 100, 1000, 10000, 100000 if none provided).
- Output: "Benchmark completed. Results written to results.csv."
- CSV Format: `n,timeNs,comparisons,swaps,accesses,allocations`


### Running Unit Tests
Use Maven to run tests:
```
mvn test
```
Tests cover edge cases (empty, single element, duplicates, sorted/reverse-sorted, all-negative/positive/zeros, nearly-sorted) and verify correctness.

## Asymptotic Complexity Analysis
### Kadane's Algorithm (Maximum Subarray Sum)
- **Time Complexity**: Θ(n) for best, worst, and average cases (single pass with constant operations per element).
- **Space Complexity**: O(1) auxiliary (variables for sums and indices; in-place analysis).
- **Recurrence**: Iterative: T(n) = T(n-1) + O(1) → T(n) = Θ(n).

### Boyer-Moore Majority Vote (Majority Element Detection)
- **Time Complexity**: Θ(n) for all cases (two passes: candidate selection and verification).
- **Space Complexity**: O(1) auxiliary (candidate and count variables).
- **Recurrence**: Iterative: T(n) = T(n-1) + O(1) → T(n) = Θ(n).

Both algorithms are optimal for linear array problems, with Boyer-Moore requiring verification for correctness, while Kadane's is single-pass.


## Code Review & Optimization
   - **Inefficiencies:** e.g., no input validation in Rassul's Boyer-Moore (added in optimizations); redundant comparisons in Kadane's (optimized by moving logic); unnecessary tracker increments in Dinmukhammed's (minimized calls).
   - **Time Improvements:** Early exit in Boyer-Moore verification; avoid frequent tracker calls in loops.
   - **Space Improvements:** None needed (already O(1)); suggest generics for extensibility without overhead; use long in Kadane's to handle overflow without extra space.
   - **Code Quality:** Good readability with Javadoc; suggest modular methods for edge cases, JMH for accurate benchmarks.

### Suggested Optimizations and Measured Improvements

- **Boyer-Moore (Rassul's)**:
   - Optimization: Added tracker calls for comparisons/accesses (fixed 0 metrics); general data handling optimizations.
   - Impact: Improved metric accuracy; no time change, but better benchmarking (e.g., n=100000: comparisons from 0 to 155517).
- **Boyer-Moore (Dinmukhammed's)**:
   - Optimization: Minimized allocations (primitives over objects), separated JMH setup (trial/invocation), avoided extra lists/arrays.
   - Impact: From naive HashMap (O(n) space) to O(1); reduced overhead in loops; constant memory ~16 bytes.
- **Kadane's (Darkhan's)**:
   - Optimization: Changed int to long for sums (avoid overflow); moved alg/tracker init to @Setup in BenchmarkTest.
   - Impact: Reduced object creation time; 10-15% savings in mixed cases.

## Empirical Validation
   - Measurements: Benchmarks on n=100-100000.
   - Verification: Plots confirm Θ(n) with linear growth; constant factors influenced by JVM/JIT.
   - Comparison: Measured times align with predictions (e.g., 10^1-10^2 ns per element).
   - Optimization Impact: e.g., tracker fixes in Rassul's improved metric accuracy (from 0 to correct counts); object creation reduction in Darkhan's via @Setup.


## Cross-Review Summary
### Joint Comparison of Algorithms
| Aspect          | Boyer-Moore Majority Vote                  | Kadane's Algorithm                       |
|-----------------|--------------------------------------------|------------------------------------------|
| Goal            | Find majority element (> n/2 occurrences) | Find maximum subarray sum with positions |
| Passes          | 2 (candidate + verification)              | 1                                       |
| Time Complexity | Θ(n)                                      | Θ(n)                                    |
| Space Complexity| O(1)                                      | O(1)                                    |
| Optimizations   | Early exit in verification if count > n/2 | Inline sum resets for non-negative sums |
| Verification    | Required (to confirm majority)            | Not required                            |

Both are efficient O(n) time, O(1) space solutions. Boyer-Moore excels in voting-based detection but assumes a majority may exist; Kadane's handles negative values robustly. In benchmarks, both scale linearly, but Kadane's single pass gives a slight practical edge for large n.


### Performance Plots
Plots are in `docs/performance-plots/`.
- Darkhan
  ![Darkhan_time_vs_n.png](docs/performance-plots/Darkhan_time_vs_n.png)
  ![Darkhan_operations_vs_n.png](docs/performance-plots/Darkhan_operations_vs_n.png)
- Rassul
  ![Rassul_time_vs_n.jpg](docs/performance-plots/Rassul_time_vs_n.jpg)
  ![Rassul_operations_vs_n.jpg](docs/performance-plots/Rassul_operations_vs_n.jpg)
- Dinmukhammed
  ![Dinmukhammed_time_vs_n.jpg](docs/performance-plots/Dinmukhammed_time_vs_n.jpg)
  ![Dinmukhammed_operations_vs_n.jpg](docs/performance-plots/Dinmukhammed_operations_vs_n.jpg)