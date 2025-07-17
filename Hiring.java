
public class Hiring {

  public static CandidateList greedyHiring(CandidateList candidates, CandidateList hired,
      int hiresLeft) {

    if (hiresLeft > 0) {
      CandidateList copyHired = hired.deepCopy();
      int n = hired.numCoveredHours();
      for (int i = 0; i < candidates.size(); i++) {
        copyHired.add(candidates.get(i));
        if (n < copyHired.numCoveredHours()) {
          n = copyHired.numCoveredHours();
          hired = copyHired.deepCopy();
        }
        copyHired.remove(candidates.get(i));
      }
      candidates.remove(hired.get(hired.size() - 1));
      return greedyHiring(candidates, hired, hiresLeft - 1);
    } else
      return hired;
  }

  public static CandidateList optimalHiring(CandidateList candidates, CandidateList hired,
      int hiresLeft) {
//    if (hiresLeft == 0) {
//      return null;
//    }
    if (candidates.isEmpty()) {
      return new CandidateList();
    }
    CandidateList bestHired = new CandidateList();
    for (Candidate candidate : candidates) {
      CandidateList newHired = hired.deepCopy();
      newHired.add(candidate);
      CandidateList remainingCandidates = candidates.deepCopy();
      remainingCandidates.remove(candidate);
      CandidateList candidateHires = optimalHiring(remainingCandidates, newHired, hiresLeft - 1);
      if (candidateHires.numCoveredHours() > bestHired.numCoveredHours()) {
        bestHired = candidateHires;
      }
    }
    return bestHired;
  }

  public static CandidateList minCoverageHiring(CandidateList candidates, CandidateList hired,
      int minHours) {
//    if (minHours <= 0) {
//      return hired;
//    }
    if (candidates.isEmpty()) {
      return null;
    }
    CandidateList bestHired = new CandidateList();
    CandidateList copy = hired.deepCopy();
    copy.add(candidates.get(0));
    double bestCost = totalPay(copy);
    for (Candidate candidate : candidates) {
      CandidateList newHired = new CandidateList(hired);
      newHired.add(candidate);
      CandidateList remainingCandidates = new CandidateList(candidates);
      remainingCandidates.remove(candidate);
      CandidateList candidateHires =
          minCoverageHiring(remainingCandidates, newHired, minHours - newHired.numCoveredHours());
      if (candidateHires != null) {
        double cost = totalPay(candidateHires);
        if (cost < bestCost) {
          bestCost = cost;
          bestHired = candidateHires;
        }
      }
    }
    return bestHired;
  }

  private static double totalPay(CandidateList list) {
    double totalPay = 0;
    for (Candidate i : list) {
      totalPay += i.getPayRate();
    }
    return totalPay;
  }
}
