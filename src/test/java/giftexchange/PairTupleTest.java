package giftexchange;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PairTupleTest {
	@Test
	public void test_createTuple() {
		PairTuple<String, String> sample = new PairTuple<>("hello", "there");
		assertEquals("First element should match expected", "hello", sample.first);
		assertEquals("Second element should match expected", "there", sample.second);
	}
}
