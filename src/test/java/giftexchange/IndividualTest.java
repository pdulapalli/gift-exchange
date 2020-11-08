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

	@Test
	public void testCheckImmediateFamilyMember_NotImmediate() {
		Individual firstPerson = createIndividual("Alice,Thomas,Linda,Gideon");
		Individual secondPerson = createIndividual("James,,,Rachael");

		assertFalse(
			"Should recognize non-immediate family member",
			firstPerson.isImmediateFamilyMember(secondPerson)
		);
	}

	@Test
	public void testCheckImmediateFamilyMember_SelfIsChild() {
		Individual firstPerson = createIndividual("Alice,Thomas,Linda,Gideon");
		Individual secondPerson = createIndividual("Thomas,,,Linda");

		assertTrue(
			"Should be an immediate family member when self is child of the other",
			firstPerson.isImmediateFamilyMember(secondPerson)
		);
	}

	@Test
	public void testCheckImmediateFamilyMember_Sibling() {
		Individual firstPerson = createIndividual("Alice,Thomas,Linda,Gideon");
		Individual secondPerson = createIndividual("Robert,Thomas,Linda");

		assertTrue(
			"Should be an immediate family member when siblings",
			firstPerson.isImmediateFamilyMember(secondPerson)
		);
	}

	@Test
	public void testCheckImmediateFamilyMember_SelfIsParent() {
		Individual firstPerson = createIndividual("Thomas,,,Linda");
		Individual secondPerson = createIndividual("Alice,Thomas,Linda,Gideon");

		assertTrue(
			"Should be an immediate family member when self is parent of the other",
			firstPerson.isImmediateFamilyMember(secondPerson)
		);
	}

	@Test
	public void testCheckImmediateFamilyMember_Spouse() {
		Individual firstPerson = createIndividual("Rachael,,,James");
		Individual secondPerson = createIndividual("James,,,Rachael");

		assertTrue(
			"Should be an immediate family member when spouses",
			firstPerson.isImmediateFamilyMember(secondPerson)
		);
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
