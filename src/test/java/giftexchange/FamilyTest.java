package giftexchange;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class FamilyTest {
	@Test
	public void test_emptyFamily() {
		InputStream sampleFamilyInfo = generateFamilySource("");
		Family fm = Family.loadFamilyInfo(sampleFamilyInfo);
		List<Individual> members = fm.getMembers();
		Hashtable<String, Individual> lookupTable = fm.getLookupTable();
		assertTrue("Family members should be empty", members.isEmpty());
		assertTrue("Family lookup table should be empty", lookupTable.isEmpty());
	}

	@Test
	public void test_nonEmptyFamily() {
		String familySourceStr = "John,Roger,Mary,Sarah\n" +
								 "Sarah,Eric,Diana,John\n" +
								 "Roger,,,Mary\n" +
								 "Mary,,,Roger\n" +
								 "Carl,John,Sarah\n" +
								 "Patricia,John,Sarah";

		InputStream sampleFamilyInfo = generateFamilySource(familySourceStr);
		Family fm = Family.loadFamilyInfo(sampleFamilyInfo);
		List<Individual> members = fm.getMembers();
		Hashtable<String, Individual> lookupTable = fm.getLookupTable();

		List<String> expectedMemberNames = Arrays.asList(
			"John", "Sarah", "Roger", "Mary", "Carl", "Patricia"
		);

		List<String> actualMemberNames = members
			.stream()
			.map(Individual::getName)
			.collect(Collectors.toList());

		assertTrue("Should have expected family members",
			expectedMemberNames.containsAll(actualMemberNames) &&
			actualMemberNames.containsAll(expectedMemberNames)
		);


		Set<String> actualLookupKeys = lookupTable.keySet();
		assertTrue("Should have expected family lookup keys",
			expectedMemberNames.containsAll(actualLookupKeys) &&
			actualLookupKeys.containsAll(expectedMemberNames)
		);
	}

	private static InputStream generateFamilySource(String familySourceStr) {
		return new ByteArrayInputStream(familySourceStr.getBytes(StandardCharsets.UTF_8));
	}
}
