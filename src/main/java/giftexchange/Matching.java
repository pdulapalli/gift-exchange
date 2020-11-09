package giftexchange;

import java.util.*;

public class Matching {
	public static HashMap<String, String> makeAssignments(List<String> membersList) {
		int numMembers = membersList.size();
		if (numMembers < 2) {
			return new HashMap<>();
		}

		Collections.shuffle(membersList, new Random(System.nanoTime()));
		HashMap<String, String> assignments = new HashMap<>();
		for (int i = 0; i < numMembers; i += 1) {
			String gifter = membersList.get(i);
			String recipient;
			if (i == 0) {
				recipient = membersList.get(numMembers - 1);
			} else {
				recipient = membersList.get(i - 1);
			}

			assignments.put(gifter, recipient);
		}

		return assignments;
	}

	public static void printAssignments(HashMap<String, String> assignments) {
		if (assignments.isEmpty()) {
			return;
		}

		System.out.println("GIFTER,RECIPIENT");
		List<String> gifters = new ArrayList<>(assignments.keySet());
		Collections.sort(gifters);
		for (String gifter : gifters) {
			String recipient = assignments.get(gifter);
			System.out.println(gifter + "," + recipient);
		}
	}
}
