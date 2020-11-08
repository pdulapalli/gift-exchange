package giftexchange;

import java.util.*;

public class Selection {
	static final int MAX_NUM_ITERATIONS = 1000;

	public static void computeExchanges(Family fm) {
		List<String> membersList = new ArrayList<>(fm.getMemberNames());
		if (membersList.size() < 2) {
			return;
		}

		boolean validlyDone = false;
		int iterationsRemaining = MAX_NUM_ITERATIONS;
		HashMap<String, String> assignments = null;

		while (!validlyDone && iterationsRemaining > 0) {
			iterationsRemaining -= 1;
			Collections.shuffle(membersList);
			assignments = makeAssignments(membersList);
			validlyDone = validateAssignments(assignments);
		}
		
		if (validlyDone) {
			printAssignments(assignments);
		}
	}

	public static HashMap<String, String> makeAssignments(List<String> membersList) {
		HashMap<String, String> assignments = new HashMap<>();
		int membersListLength = membersList.size();
		for (int i = 0; i < membersListLength; i += 1) {
			String gifter = membersList.get(i);
			String recipient = membersList.get(getPreviousIndexWrap(i, membersListLength));
			assignments.put(gifter, recipient);
		}

		return assignments;
	}

	public static boolean validateAssignments(HashMap<String, String> assignments) {
		if (assignments.isEmpty()) {
			return false;
		}

		for (Map.Entry<String, String> entry : assignments.entrySet()) {
			String gifter = entry.getKey();
			String recipient = entry.getValue();

			if (gifter.equals(recipient)) {
				return false;
			}
		}

		return true;
	}

	public static void printAssignments(HashMap<String, String> assignments) {
		if (assignments.isEmpty()) {
			return;
		}

		System.out.println("GIFTER -> RECIPIENT");
		for (Map.Entry<String, String> entry : assignments.entrySet()) {
			String gifter = entry.getKey();
			String recipient = entry.getValue();
			System.out.println(gifter + " -> " + recipient);
		}
	}

	private static int getPreviousIndexWrap(int currentIndex, int length) {
		if (length <= 0) {
			return 0;
		}

		if (currentIndex == 0) {
			return length - 1;
		}

		return currentIndex - 1;
	}
}
