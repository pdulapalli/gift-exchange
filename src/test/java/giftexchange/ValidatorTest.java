package giftexchange;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.List;

import static org.junit.Assert.*;

public class ValidatorTest {
	@Test
	public void test_validPairingsNotSelf() {
		Family fam = generateFamily();
		Hashtable<String, Individual> lookupTable = fam.getLookupTable();
		Validator v = new Validator(lookupTable);

		List<PairTuple<String, String>> validPairings = v.getValidPairings(v::notPairedToSelf);
		for (PairTuple<String, String> pairing : validPairings) {
			assertNotNull(pairing.first);
			assertNotEquals(pairing.first, pairing.second);
		}
	}

	@Test
	public void test_validPairingsNotImmediateFamily() {
		Family fam = generateFamily();
		Hashtable<String, Individual> lookupTable = fam.getLookupTable();
		Validator v = new Validator(lookupTable);

		List<PairTuple<String, String>> validPairings =
			v.getValidPairings(v::notPairedToImmediateFamily);
		for (PairTuple<String, String> pairing : validPairings) {
			assertNotNull(pairing.first);
			Individual personA = lookupTable.get(pairing.first);
			Individual personB = lookupTable.get(pairing.second);
			assertFalse(personA.isImmediateFamilyMember(personB));
		}
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
