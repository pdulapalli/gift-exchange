package giftexchange;

import java.util.HashMap;

public class App {
	public static void main(String[] args) {
		Family fam = Family.loadFamilyInfo(System.in);

		try {
			HashMap<String, String> assignments =
				Matching.makeAssignments(fam.getMemberNames());
			Matching.printAssignments(assignments);
		} catch (RuntimeException exc) {
			System.err.println("Unable to compute gift exchange matches");
		}
	}
}
