package giftexchange;

import java.util.List;

public class App {
	public static void main(String[] args) {
		boolean strictPairingsOnly = true;
		for (String arg : args) {
			if (arg.equals("--loose-constraints")) {
				strictPairingsOnly = false;
			}
		}

		Family fam = Family.loadFamilyInfo(System.in);
		Validator v = new Validator(fam.getLookupTable());

		List<PairTuple<String, String>> validPairings;
		if (strictPairingsOnly) {
			validPairings = v.getValidPairings(v::notPairedToImmediateFamily);
		} else {
			validPairings = v.getValidPairings(v::notPairedToSelf);
		}

		try {
			List<PairTuple<String, String>> assignments =
				Selector.makeAssignments(fam.getMemberNames(), validPairings);
			Selector.printAssignments(assignments);
		} catch (RuntimeException exc) {
			System.err.println("Unable to compute gift exchange matches");
		}
	}
}
