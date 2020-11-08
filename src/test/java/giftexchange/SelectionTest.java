package giftexchange;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SelectionTest {
	@Test
	public void test_makeAssignments() {
		List<String> membersList = Arrays.asList("Anna", "Brian", "Cara", "Daniel", "Elsie");
		HashMap<String, String> assignments = Selection.makeAssignments(membersList);
		final Set<String> gifterNames = assignments.keySet();
		assertTrue("Members should be given assignments",
			membersList.containsAll(gifterNames) &&
			gifterNames.containsAll(membersList)
		);
	}

	@Test
	public void test_passValidAssignmentsCheck() {
		HashMap<String, String> assignments = new HashMap<>();
		assignments.put("Alice", "Erin");
		assignments.put("Erin", "David");
		assignments.put("David", "Christine");
		assignments.put("Christine", "Bill");
		assignments.put("Bill", "Alice");

		assertTrue("Assignments should be valid", Selection.validateAssignments(assignments));
	}

	@Test
	public void test_failInvalidAssignmentsCheck() {
		HashMap<String, String> assignments = new HashMap<>();
		assignments.put("Alice", "Alice"); // Gifter cannot be their own recipient
		assignments.put("Erin", "David");
		assignments.put("David", "Christine");
		assignments.put("Christine", "Bill");
		assignments.put("Bill", "Alice");

		assertFalse("Assignments should NOT be valid", Selection.validateAssignments(assignments));
	}
}
