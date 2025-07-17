# TA Hiring Optimization System 👨‍🏫📊

A Java-based optimization system for hiring Teaching Assistants (TAs) that solves complex scheduling and budget constraints using recursive algorithms. Features multiple optimization strategies including greedy hiring, optimal coverage maximization, and minimum cost coverage.

## 🌟 Features

- **Multiple Optimization Strategies** - Greedy, optimal, and cost-minimization algorithms
- **Flexible Scheduling** - Support for any number of time slots and availability patterns
- **Budget-Aware Hiring** - Cost optimization with pay rate considerations
- **Comprehensive Testing** - Base case, recursive, and fuzz testing suites
- **Interactive CLI** - Command-line interface for real-world usage
- **Extensible Design** - Modular architecture for adding new optimization strategies

## 🚀 Quick Start

### Prerequisites

- Java 8 or higher
- Basic understanding of optimization algorithms and recursion

### Installation

1. **Download the project files:**
```bash
git clone https://github.com/yourusername/ta-hiring-system.git
cd ta-hiring-system
```

2. **Compile the project:**
```bash
javac *.java
```

3. **Run the interactive driver:**
```bash
java HiringDriver
```

4. **Or run the test suite:**
```bash
java HiringTesting
```

## 🎮 How to Use

### Interactive Mode

#### Example 1: Maximum Coverage Problem
```
Input:
6 4 1 2
true true true false false true
true true false true false true
true false true false true true
false false true true false false

Output:
Greedy Solution: 0 1
Hours covered: 5
Optimal Solution: 1 2
Hours covered: 6
```

#### Example 2: Minimum Cost Problem
```
Input:
6 4 2 5
true true true false false true 5
true true false true false true 1
true false true false true true 5
false false true true false false 5

Output:
Optimal Solution: 0 1
Hours covered: 5
Cost: 6
```

### Input Format
```
<number of hours> <number of TAs> <type of problem> <constraint>
<ta availability> [pay rate]
...
```

Where:
- **Problem Type 1**: Maximize hours covered with limited hires
- **Problem Type 2**: Minimize cost while meeting hour requirements

## 🏗️ System Architecture

### Core Classes

#### 1. **Candidate.java** - TA Candidate Representation
```java
public class Candidate {
    private final int candidateId;          // Unique identifier
    private final boolean[] availability;    // Time slot availability
    private final int payRate;              // Hourly pay rate (-1 if not set)
    
    public boolean[] hire(boolean[] hoursNeeded); // Calculate coverage
    public boolean isAvailable(int hour);         // Check specific availability
}
```

#### 2. **CandidateList.java** - Extended ArrayList for Candidates
```java
public class CandidateList extends ArrayList<Candidate> {
    public int numCoveredHours();                    // Calculate total coverage
    public int totalCost();                          // Calculate total hiring cost
    public CandidateList withoutCandidate(Candidate c); // Immutable operations
    public CandidateList withCandidate(Candidate c);
}
```

#### 3. **Hiring.java** - Core Optimization Algorithms
```java
public class Hiring {
    // Greedy approach: select best candidate at each step
    public static CandidateList greedyHiring(CandidateList candidates, 
                                            CandidateList hired, int hiresLeft);
    
    // Optimal approach: exhaustive search for maximum coverage
    public static CandidateList optimalHiring(CandidateList candidates, 
                                             CandidateList hired, int hiresLeft);
    
    // Cost-optimal approach: minimize cost while meeting requirements
    public static CandidateList minCoverageHiring(CandidateList candidates, 
                                                 CandidateList hired, int minHours);
}
```

#### 4. **HiringDriver.java** - Interactive Command-Line Interface
```java
public class HiringDriver {
    public static void main(String[] args); // Main interactive loop
    private static int readIntOrError(Scanner sc, String errMsg);
    private static boolean[] readNBoolsOrError(Scanner sc, int n, String errMsg);
}
```

## 🧮 Optimization Algorithms

### 1. Greedy Hiring Algorithm

#### Approach
Select the candidate that provides the maximum additional hour coverage at each step.

```java
public static CandidateList greedyHiring(CandidateList candidates, 
                                        CandidateList hired, int hiresLeft) {
    if (hiresLeft > 0) {
        // Find candidate that maximizes additional coverage
        Candidate bestCandidate = findBestCandidate(candidates, hired);
        
        // Recursively hire remaining candidates
        return greedyHiring(remainingCandidates, updatedHired, hiresLeft - 1);
    }
    return hired;
}
```

#### Time Complexity: O(n² * h)
- n = number of candidates
- h = number of hours
- For each hire, evaluate all remaining candidates

### 2. Optimal Hiring Algorithm

#### Approach
Exhaustively explore all possible combinations to find globally optimal solution.

```java
public static CandidateList optimalHiring(CandidateList candidates, 
                                         CandidateList hired, int hiresLeft) {
    if (candidates.isEmpty()) return new CandidateList();
    
    CandidateList bestSolution = new CandidateList();
    
    // Try hiring each candidate
    for (Candidate candidate : candidates) {
        CandidateList withCandidate = hired.withCandidate(candidate);
        CandidateList remaining = candidates.withoutCandidate(candidate);
        
        // Recursively solve subproblem
        CandidateList solution = optimalHiring(remaining, withCandidate, hiresLeft - 1);
        
        // Update best solution if better
        if (solution.numCoveredHours() > bestSolution.numCoveredHours()) {
            bestSolution = solution;
        }
    }
    
    return bestSolution;
}
```

#### Time Complexity: O(n!)
- Explores all permutations of candidate selections
- Guaranteed to find globally optimal solution

### 3. Minimum Coverage Hiring Algorithm

#### Approach
Find the least expensive combination of candidates that meets minimum hour requirements.

```java
public static CandidateList minCoverageHiring(CandidateList candidates, 
                                             CandidateList hired, int minHours) {
    if (candidates.isEmpty()) return null;
    
    CandidateList bestSolution = null;
    double bestCost = Double.MAX_VALUE;
    
    for (Candidate candidate : candidates) {
        CandidateList withCandidate = hired.withCandidate(candidate);
        int remainingHours = minHours - withCandidate.numCoveredHours();
        
        if (remainingHours <= 0) {
            // Requirements met, check cost
            if (withCandidate.totalCost() < bestCost) {
                bestCost = withCandidate.totalCost();
                bestSolution = withCandidate;
            }
        } else {
            // Need more candidates
            CandidateList remaining = candidates.withoutCandidate(candidate);
            CandidateList solution = minCoverageHiring(remaining, withCandidate, remainingHours);
            
            if (solution != null && solution.totalCost() < bestCost) {
                bestCost = solution.totalCost();
                bestSolution = solution;
            }
        }
    }
    
    return bestSolution;
}
```

## 📊 Performance Analysis

### Algorithm Comparison

| Algorithm | Time Complexity | Space Complexity | Optimality | Use Case |
|-----------|----------------|------------------|------------|----------|
| Greedy | O(n² * h) | O(n) | Local optimum | Quick approximation |
| Optimal | O(n!) | O(n * depth) | Global optimum | Small datasets |
| Min Coverage | O(n!) | O(n * depth) | Global optimum | Budget constraints |

### Scalability Considerations

#### Greedy Algorithm
- **Suitable for**: Large datasets (n > 20)
- **Limitation**: May miss globally optimal solutions
- **Performance**: Consistent, predictable runtime

#### Optimal Algorithms
- **Suitable for**: Small to medium datasets (n ≤ 15)
- **Limitation**: Exponential time complexity
- **Performance**: Guaranteed optimal but computationally expensive

## 🛠️ Development

### Testing Framework

#### Base Case Testing
```java
public static boolean greedyHiringBaseTest() {
    // Test with hiresLeft = 0
    CandidateList result = Hiring.greedyHiring(candidates, hired, 0);
    return result.isEmpty(); // Should return empty list
}
```

#### Recursive Case Testing
```java
public static boolean greedyHiringRecursiveTest() {
    // Test with valid inputs that trigger recursion
    CandidateList result = Hiring.greedyHiring(candidates, hired, hiresLeft);
    return !result.isEmpty(); // Should return non-empty solution
}
```

#### Fuzz Testing
```java
public static boolean optimalHiringFuzzTest() {
    Random randGen = new Random();
    for (int i = 0; i < 100; i++) {
        // Generate random test cases
        CandidateList candidates = generateRandomInput();
        CandidateList expected = allOptimalSolutions(candidates);
        CandidateList actual = Hiring.optimalHiring(candidates, new CandidateList(), hires);
        
        if (!compareCandidateLists(expected, actual)) {
            return false;
        }
    }
    return true;
}
```

### Adding New Optimization Strategies

#### Custom Strategy Example
```java
public static CandidateList priorityHiring(CandidateList candidates, 
                                          CandidateList hired, 
                                          int hiresLeft,
                                          String[] priorities) {
    // Sort candidates by priority criteria
    candidates.sort((c1, c2) -> comparePriority(c1, c2, priorities));
    
    // Apply greedy selection with priority ordering
    return greedyHiring(candidates, hired, hiresLeft);
}

private static int comparePriority(Candidate c1, Candidate c2, String[] priorities) {
    for (String priority : priorities) {
        switch (priority) {
            case "cost":
                return Integer.compare(c1.getPayRate(), c2.getPayRate());
            case "coverage":
                return Integer.compare(
                    countAvailableHours(c2), 
                    countAvailableHours(c1)
                );
            case "efficiency":
                return Double.compare(
                    getEfficiencyRatio(c2), 
                    getEfficiencyRatio(c1)
                );
        }
    }
    return 0;
}
```

### Advanced Features

#### Constraint-Based Hiring
```java
public class ConstraintBasedHiring {
    public static CandidateList hireWithConstraints(
        CandidateList candidates,
        HiringConstraints constraints
    ) {
        // Filter candidates by constraints
        CandidateList filtered = candidates.stream()
            .filter(c -> meetsConstraints(c, constraints))
            .collect(CandidateList::new, CandidateList::add, CandidateList::addAll);
        
        // Apply optimization algorithm
        return optimalHiring(filtered, new CandidateList(), constraints.maxHires);
    }
}

public class HiringConstraints {
    public int maxBudget;
    public int maxHires;
    public int minHours;
    public boolean[] requiredCoverage;  // Must-cover time slots
    public String[] preferredSkills;
}
```

#### Multi-Objective Optimization
```java
public class MultiObjectiveHiring {
    public static CandidateList paretoOptimalHiring(
        CandidateList candidates,
        ObjectiveWeights weights
    ) {
        // Score each combination based on multiple objectives
        List<CandidateList> solutions = generateAllSolutions(candidates);
        
        return solutions.stream()
            .max((s1, s2) -> Double.compare(
                calculateScore(s1, weights),
                calculateScore(s2, weights)
            ))
            .orElse(new CandidateList());
    }
    
    private static double calculateScore(CandidateList solution, ObjectiveWeights weights) {
        return weights.coverageWeight * solution.numCoveredHours() +
               weights.costWeight * (1.0 / solution.totalCost()) +
               weights.diversityWeight * calculateDiversity(solution);
    }
}
```

## 🔧 Configuration

### Candidate Creation
```java
// Create candidate with availability only
boolean[] availability = {true, false, true, true, false};
Candidate candidate1 = new Candidate(availability);

// Create candidate with availability and pay rate
Candidate candidate2 = new Candidate(availability, 15); // $15/hour
```

### Problem Setup
```java
// Maximum coverage problem
CandidateList solution1 = Hiring.optimalHiring(candidates, new CandidateList(), 3);

// Minimum cost problem
CandidateList solution2 = Hiring.minCoverageHiring(candidates, new CandidateList(), 20);
```

### Testing Configuration
```java
// Generate random test cases
CandidateList testCandidates = HiringTestingUtilities.generateRandomInput(
    numHours: 8,
    numCandidates: 10,
    maxPayRate: 25
);
```

## 🐛 Troubleshooting

### Common Issues

1. **Stack Overflow in Recursive Calls**
   ```java
   // Add base case validation
   public static CandidateList optimalHiring(...) {
       if (hiresLeft <= 0 || candidates.isEmpty()) {
           return hired; // Prevent infinite recursion
       }
       // ... rest of algorithm
   }
   ```

2. **Inconsistent Results in Testing**
   ```java
   // Ensure deterministic behavior
   public static Random randGen = new Random(205); // Fixed seed
   
   // Reset candidate IDs for testing
   public static void resetCandidateIds() {
       Candidate.nextCandidateId = 0; // If made package-private
   }
   ```

3. **Performance Issues with Large Datasets**
   ```java
   // Add early termination for large problems
   public static CandidateList optimalHiring(...) {
       if (candidates.size() > 15) {
           System.out.println("Warning: Large dataset, using greedy approximation");
           return greedyHiring(candidates, hired, hiresLeft);
       }
       // ... optimal algorithm
   }
   ```

### Debug Techniques

#### Visualization
```java
public static void visualizeSchedule(CandidateList hired) {
    System.out.println("Hired TAs Schedule:");
    System.out.print("Hour:     ");
    for (int h = 0; h < 24; h++) {
        System.out.printf("%2d ", h);
    }
    System.out.println();
    
    for (Candidate c : hired) {
        System.out.printf("TA %d:     ", c.getId());
        for (int h = 0; h < 24; h++) {
            System.out.print(c.isAvailable(h) ? " # " : " . ");
        }
        System.out.println();
    }
}
```

#### Performance Monitoring
```java
public static CandidateList timedOptimalHiring(CandidateList candidates, 
                                              CandidateList hired, 
                                              int hiresLeft) {
    long startTime = System.nanoTime();
    CandidateList result = optimalHiring(candidates, hired, hiresLeft);
    long endTime = System.nanoTime();
    
    System.out.printf("Optimal hiring took %.2f ms%n", 
                     (endTime - startTime) / 1_000_000.0);
    return result;
}
```

## 📈 Real-World Applications

### University TA Scheduling
- **Use Case**: Assign TAs to cover all lab sessions
- **Constraints**: Budget limits, minimum coverage requirements
- **Optimization**: Minimize cost while ensuring adequate coverage

### Call Center Staffing
- **Use Case**: Schedule agents across time zones
- **Constraints**: Service level agreements, labor costs
- **Optimization**: Balance cost and customer satisfaction

### Medical Resident Scheduling
- **Use Case**: Assign residents to hospital shifts
- **Constraints**: Work hour limits, specialty requirements
- **Optimization**: Ensure coverage while respecting regulations

## 🔮 Future Enhancements

### Planned Features
- [ ] GUI interface for visual schedule management
- [ ] Database integration for persistent candidate storage
- [ ] Advanced constraint support (skills, preferences)
- [ ] Real-time optimization with schedule changes
- [ ] Machine learning for demand prediction
- [ ] Multi-campus support

### Advanced Algorithms
- [ ] Genetic algorithm for large-scale optimization
- [ ] Simulated annealing for approximate solutions
- [ ] Integer linear programming integration
- [ ] Dynamic programming optimizations
- [ ] Parallel processing for recursive algorithms

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Add your improvements
4. Run comprehensive tests
5. Submit a pull request

### Contribution Guidelines
- Follow Java coding standards
- Add tests for new algorithms
- Document optimization trade-offs
- Maintain backward compatibility
- Include performance benchmarks

## 🆘 Support

If you encounter issues:

1. Run the test suite to verify algorithm correctness
2. Check input format and constraints
3. Monitor memory usage for large datasets
4. Test with smaller problem sizes first
5. Open an issue with algorithm details and test cases

---

**Optimize your hiring decisions!** 📈👥

*Built with ❤️ by Rishabh Aggarwal & Krishna Sai Maganti*

### Academic Integrity Notice
This project was created for educational purposes as part of CS 300 coursework. Please respect academic integrity policies when using this code for learning or reference.
