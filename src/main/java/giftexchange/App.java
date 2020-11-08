package giftexchange;

import java.util.HashMap;

public class App {
	public static void main(String[] args) {
		Family fam = Family.loadFamilyInfo(System.in);
		HashMap<String, String> assignments = Selector.makeAssignments(fam);
		Selector.printAssignments(assignments);
	}
}
