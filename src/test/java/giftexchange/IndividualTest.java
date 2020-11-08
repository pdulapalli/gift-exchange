package giftexchange;

import org.junit.Test;

import static org.junit.Assert.*;

public class IndividualTest {
	@Test
	public void testParseRecord_ParentsAndSpouse() {
		String sampleRecord = "John,Roger,Mary,Sarah";
		Individual parsed = createIndividual(sampleRecord);

		assertNotNull("Should have parsed the record", parsed);
		assertEquals("Individual name should be correct", "John", parsed.getName());
		assertEquals("Individual spouse should be correct", "Sarah", parsed.getSpouse());

		String[] parentsExpected = new String[]{"Roger", "Mary"};
		assertArrayEquals("Individual parents should be correct", parentsExpected, parsed.getParents());
	}

	@Test
	public void testParseRecord_ParentsNoSpouse() {
		String sampleRecord = "Patricia,John,Sarah";
		Individual parsed = createIndividual(sampleRecord);

		assertNotNull("Should have parsed the record", parsed);
		assertEquals("Individual name should be correct", "Patricia", parsed.getName());
		assertNull("Individual should not have a spouse", parsed.getSpouse());

		String[] parentsExpected = new String[]{"John", "Sarah"};
		assertArrayEquals("Individual parents should be correct", parentsExpected, parsed.getParents());
	}

	@Test
	public void testParseRecord_SpouseNoParents() {
		String sampleRecord = "Roger,,,Mary";
		Individual parsed = createIndividual(sampleRecord);

		assertNotNull("Should have parsed the record", parsed);
		assertEquals("Individual name should be correct", "Roger", parsed.getName());
		assertEquals("Individual spouse should be correct", "Mary", parsed.getSpouse());

		String[] parentsExpected = new String[]{};
		assertArrayEquals("Individual should not have parents", parentsExpected, parsed.getParents());
	}

	private static Individual createIndividual(String sampleRecord) {
		Individual parsed = null;
		try {
			parsed = Individual.parseMember(sampleRecord);
		} catch (Exception ignored) {
		}

		return parsed;
	}
}
