package giftexchange;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class SelectorTest {
	@Test
	public void test_makeAssignments() {
		Family fam = generateFamily();
		HashMap<String, String> assignments = Selector.makeAssignments(fam);
		final List<String> membersList = fam.getMemberNames();
		final Set<String> gifterNames = assignments.keySet();
		assertTrue("Members should be given assignments",
			membersList.containsAll(gifterNames) &&
			gifterNames.containsAll(membersList)
		);
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
