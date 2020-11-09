package giftexchange;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class MatchingTest {
	@Test
	public void test_makeAssignments_noPairingToSelf() {
		Family fam = generateFamily_1();
		HashMap<String, String> assignments =
			Matching.makeAssignments(fam.getMemberNames());

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

	private static InputStream generateFamilySource(String familySourceStr) {
		return new ByteArrayInputStream(familySourceStr.getBytes(StandardCharsets.UTF_8));
	}
}
