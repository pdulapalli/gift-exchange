package giftexchange;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.Assert.assertTrue;

public class MatchingTest {
	@Test
	public void test_makeAssignments_withPresetValidPairings() {
		Family fam = generateFamily_1();
		HashMap<String, String> assignments =
			Matching.makeAssignments(fam.getMemberNames(), generateValidPairings());

		final String pairingInvalidMsg = "Pairing should match expected";
		for (String gifter : assignments.keySet()) {
			String recipient = assignments.get(gifter);
			assertTrue(pairingInvalidMsg, checkAssignment(gifter, recipient));
		}
	}

	@Test
	public void test_makeAssignments_allowImmediateFamily() {
		Family fam = generateFamily_1();
		Validator v = new Validator(fam.getLookupTable());

		List<PairTuple<String, String>> validPairings = v.getValidPairings(v::notPairedToSelf);
		HashMap<String, String> assignments =
			Matching.makeAssignments(fam.getMemberNames(), validPairings);

		final String pairingInvalidMsg = "Should not be paired to self";
		Set<String> gifters = assignments.keySet();
		Set<String> recipients = new HashSet<>(assignments.values());

		assertTrue(gifters.containsAll(recipients));
		assertTrue(recipients.containsAll(gifters));
		for (String gifter : gifters) {
			String recipient = assignments.get(gifter);
			assertTrue(pairingInvalidMsg, !gifter.equals(recipient));
		}
	}

	@Test
	public void test_makeAssignments_doNotAllowImmediateFamily() {
		Family fam = generateFamily_2();
		Hashtable<String, Individual> lookupTable = fam.getLookupTable();
		Validator v = new Validator(lookupTable);

		List<PairTuple<String, String>> validPairings =
			v.getValidPairings(v::notPairedToImmediateFamily);
		HashMap<String, String> assignments =
			Matching.makeAssignments(fam.getMemberNames(), validPairings);

		final String pairingInvalidMsg = "Should not be paired to immediate family";
		Set<String> gifterNames = assignments.keySet();
		Set<String> recipients = new HashSet<>(assignments.values());

		assertTrue(gifterNames.containsAll(recipients));
		assertTrue(recipients.containsAll(gifterNames));

		for (String gifterName : gifterNames) {
			Individual gifter = lookupTable.get(gifterName);
			Individual recipient = lookupTable.get(assignments.get(gifterName));
			assertTrue(pairingInvalidMsg, !gifter.isImmediateFamilyMember(recipient));
		}
	}

	private static boolean checkAssignment(String gifter, String recipient) {
		switch (gifter) {
			case "John":
				return recipient.equals("Roger");
			case "Sarah":
				return recipient.equals("Mary");
			case "Roger":
				return recipient.equals("Carl");
			case "Mary":
				return recipient.equals("Patricia");
			case "Carl":
				return recipient.equals("John");
			case "Patricia":
				return recipient.equals("Sarah");
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

	private static Family generateFamily_1() {
		String familySourceStr = "John,Roger,Mary,Sarah\n" +
								 "Sarah,Eric,Diana,John\n" +
								 "Roger,,,Mary\n" +
								 "Mary,,,Roger\n" +
								 "Carl,John,Sarah\n" +
								 "Patricia,John,Sarah";

		InputStream sampleFamilyInfo = generateFamilySource(familySourceStr);
		return Family.loadFamilyInfo(sampleFamilyInfo);
	}

	private static Family generateFamily_2() {
		String familySourceStr = "Angus,,,Melinda\n" +
								 "Beckett,,,Harriett\n" +
								 "Clifford,Angus,Melinda,Wilma\n" +
								 "Sylvia,Angus,Melinda,Trenton\n" +
								 "Trenton,Ellis,Shirley,Sylvia\n" +
								 "Nigel,Beckett,Harriett,Ciara\n" +
								 "Ciara,Jermaine,Roxanne,Nigel\n" +
								 "Wilma,Beckett,Harriett,Clifford";

		InputStream sampleFamilyInfo = generateFamilySource(familySourceStr);
		return Family.loadFamilyInfo(sampleFamilyInfo);
	}

	private static InputStream generateFamilySource(String familySourceStr) {
		return new ByteArrayInputStream(familySourceStr.getBytes(StandardCharsets.UTF_8));
	}
}
