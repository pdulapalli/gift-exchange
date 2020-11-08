package giftexchange;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SelectorTest {
	@Test
	public void test_makeAssignments() {
		Family fam = generateFamily();
		List<PairTuple<String, String>> assignments =
			Selector.makeAssignments(fam.getMemberNames(), generateValidPairings());

		final String pairingInvalidMsg = "Pairing should match expected";
		for (PairTuple<String, String> assignment : assignments) {
			assertTrue(pairingInvalidMsg, checkAssignment(assignment));
		}
	}

	private static boolean checkAssignment(PairTuple<String, String> assignment) {
		switch (assignment.first) {
			case "John":
				return assignment.second.equals("Roger");
			case "Sarah":
				return assignment.second.equals("Mary");
			case "Roger":
				return assignment.second.equals("Carl");
			case "Mary":
				return assignment.second.equals("Patricia");
			case "Carl":
				return assignment.second.equals("John");
			case "Patricia":
				return assignment.second.equals("Sarah");
			default:
				return false;
		}
	}

	private static List<PairTuple<String, String>> generateValidPairings() {
		List<PairTuple<String, String>> pairings = new ArrayList<>();
		pairings.add(new PairTuple<>("John", "Roger"));
		pairings.add(new PairTuple<>("Sarah", "Mary"));
		pairings.add(new PairTuple<>("Roger", "Carl"));
		pairings.add(new PairTuple<>("Mary", "Patricia"));
		pairings.add(new PairTuple<>("Carl", "John"));
		pairings.add(new PairTuple<>("Patricia", "Sarah"));

		return pairings;
	}

	private static Family generateFamily() {
		String familySourceStr = "John,Roger,Mary,Sarah\n" +
								 "Sarah,Eric,Diana,John\n" +
								 "Roger,,,Mary\n" +
								 "Mary,,,Roger\n" +
								 "Carl,John,Sarah\n" +
								 "Patricia,John,Sarah";

		InputStream sampleFamilyInfo = generateFamilySource(familySourceStr);
		return Family.loadFamilyInfo(sampleFamilyInfo);
	}

	private static InputStream generateFamilySource(String familySourceStr) {
		return new ByteArrayInputStream(familySourceStr.getBytes(StandardCharsets.UTF_8));
	}
}
