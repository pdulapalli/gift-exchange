package giftexchange;

import java.util.List;

public class App {
	public static void main(String[] args) {
		Family fam = Family.loadFamilyInfo(System.in);
		Validator v = new Validator(fam.getLookupTable());
		List<PairTuple<String, String>> validPairings = v.getValidPairings(v::notPairedToSelf);
		List<PairTuple<String, String>> assignments =
			Selector.makeAssignments(fam.getMemberNames(), validPairings);
		Selector.printAssignments(assignments);
	}
}
