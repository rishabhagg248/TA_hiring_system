//////////////// Hiring Tester //////////////////////////
//
// Title: A tester for the hiring class methods
// Course: CS 300 Fall 2023
//
// Author: Rishabh Aggarwal
// Email: raggarwal24@wisc.edu
// Lecturer: Mouna Kacem
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: Krishna Sai Maganti
// Partner Email: kmaganti@wisc.edu
// Partner Lecturer's Name: Hobbes Legault
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// _X_ Write-up states that pair programming is allowed for this assignment.
// _X_ We have both read and understand the course Pair Programming Policy.
// _X_ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: Null
// Online Sources: Null
//
///////////////////////////////////////////////////////////////////////////////

//importing all required files
import java.util.Random;
import java.util.ArrayList;

/*
 * this class performs base test, recursive test and fuzz test on all methods of hiring class
 */
public class HiringTesting {

  /*
   * base case tester for greedy hiring method
   */
  public static boolean greedyHiringBaseTest() {

    // Case 1: if hires left = 0
    Random randGen = new Random();
    int numHours = randGen.nextInt(5) + 1;
    int numCandidates = randGen.nextInt(10) + 1;
    CandidateList candidates = HiringTestingUtilities.generateRandomInput(numHours, numCandidates);
    CandidateList hired = new CandidateList();
    int hiresLeft = 0;
    if (!Hiring.greedyHiring(candidates, hired, hiresLeft).isEmpty()) {
      return false;
    }

    // Case 2: if candidates CandidateList is empty
    CandidateList candidatesEmpty = new CandidateList();
    hiresLeft = numCandidates;
    if (!Hiring.greedyHiring(candidatesEmpty, hired, hiresLeft).isEmpty()) {
      return false;
    }

    return true;

  }

  /*
   * recursive case tester for greedy hiring method
   */
  public static boolean greedyHiringRecursiveTest() {

    Random randGen = new Random();
    int numHours = randGen.nextInt(5) + 1;
    int numCandidates = randGen.nextInt(9) + 2;
    CandidateList candidates = HiringTestingUtilities.generateRandomInput(numHours, numCandidates);
    CandidateList hired = new CandidateList();
    int hiresLeft = numCandidates - 1;
    if (Hiring.greedyHiring(candidates, hired, hiresLeft).isEmpty()) {
      return false;
    }

    return true;

  }

  /*
   * base case tester for optimal hiring method
   */
  public static boolean optimalHiringBaseTest() {
    
    // Case 1: if hires left = 0
    Random randGen = new Random();
    int numHours = randGen.nextInt(5) + 1;
    int numCandidates = randGen.nextInt(10) + 1;
    CandidateList candidates = HiringTestingUtilities.generateRandomInput(numHours, numCandidates);
    CandidateList hired = new CandidateList();
    int hiresLeft = 0;
    if (!Hiring.optimalHiring(candidates, hired, hiresLeft).isEmpty()) {
      return false;
    }

    // Case 2: if candidates CandidateList is empty
    CandidateList candidatesEmpty = new CandidateList();
    hiresLeft = numCandidates;
    if (!Hiring.optimalHiring(candidatesEmpty, hired, hiresLeft).isEmpty()) {
      return false;
    }

    return true;
    
  }

  /*
   * recursive case tester for optimal hiring method
   */
  public static boolean optimalHiringRecursiveTest() {
    
    Random randGen = new Random();
    int numHours = randGen.nextInt(5) + 1;
    int numCandidates = randGen.nextInt(9) + 2;
    CandidateList candidates = HiringTestingUtilities.generateRandomInput(numHours, numCandidates);
    CandidateList hired = new CandidateList();
    int hiresLeft = numCandidates - 1;
    if (Hiring.optimalHiring(candidates, hired, hiresLeft).isEmpty()) {
      return false;
    }

    return true;
    
  }

  /*
   * fuzz tester for optimal hiring method
   */
  public static boolean optimalHiringFuzzTest() {

    Random randGen = new Random();
    int runner = randGen.nextInt(100) + 100;

    for (int i = 0; i < runner; i++) {
      int numHours = randGen.nextInt(5) + 1;
      int numCandidates = randGen.nextInt(10) + 1;
      CandidateList candidates =
          HiringTestingUtilities.generateRandomInput(numHours, numCandidates);
      int numHires = randGen.nextInt(numCandidates) + 1;
      ArrayList<CandidateList> expectedCandidates = new ArrayList<>();
      expectedCandidates = HiringTestingUtilities.allOptimalSolutions(candidates, numHires);
      CandidateList hired = new CandidateList();
      int hires = numCandidates;
      CandidateList actualCandidates = Hiring.optimalHiring(candidates,hired,hires);
      if (!HiringTestingUtilities.compareCandidateLists(expectedCandidates, actualCandidates)) {
        return false;
      }
    }

    return true;

  }

  public static boolean minCoverageHiringBaseTest() {
 // Case 1: if minhours = 0
    Random randGen = new Random();
    int numHours = randGen.nextInt(5) + 1;
    int numCandidates = randGen.nextInt(10) + 1;
    CandidateList candidates = HiringTestingUtilities.generateRandomInput(numHours, numCandidates);
    CandidateList hired = new CandidateList();
    int minHours = 0;
    if (!Hiring.optimalHiring(candidates, hired, minHours).isEmpty()) {
      return false;
    }

    // Case 2: if candidates CandidateList is empty
    CandidateList candidatesEmpty = new CandidateList();
    minHours = numHours;
    if (!Hiring.optimalHiring(candidatesEmpty, hired, minHours).isEmpty()) {
      return false;
    }

    return true;
    
  }

  public static boolean minCoverageHiringRecursiveTest() {
    
    Random randGen = new Random();
    int numHours = randGen.nextInt(5) + 1;
    int numCandidates = randGen.nextInt(9) + 2;
    CandidateList candidates = HiringTestingUtilities.generateRandomInput(numHours, numCandidates);
    CandidateList hired = new CandidateList();
    int minHours = numHours - 1;
    if (Hiring.optimalHiring(candidates, hired, minHours).isEmpty()) {
      return false;
    }

    return true;
  
  }

  public static boolean minCoverageHiringFuzzTest() {

    Random randGen = new Random();
    int runner = randGen.nextInt(100) + 100;

    for (int i = 0; i < runner; i++) {
      int numHours = randGen.nextInt(5) + 1;
      int numCandidates = randGen.nextInt(10) + 1;
      CandidateList candidates =
          HiringTestingUtilities.generateRandomInput(numHours, numCandidates);
      int minHours = randGen.nextInt(numHours) + 1;
      ArrayList<CandidateList> expectedCandidates = new ArrayList<>();
      expectedCandidates = HiringTestingUtilities.allMinCoverageSolutions(candidates, minHours);
      CandidateList hired = new CandidateList();
      CandidateList actualCandidates = Hiring.minCoverageHiring(candidates,hired,minHours);
      if (!HiringTestingUtilities.compareCandidateLists(expectedCandidates, actualCandidates)) {
        return false;
      }
    }

    return true;

  }

}
