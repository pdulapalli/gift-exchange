package giftexchange;

import org.psjava.algo.graph.matching.HopcroftKarpAlgorithm;
import org.psjava.algo.graph.matching.MaximumBipartiteMatchingResult;
import org.psjava.ds.graph.MutableBipartiteGraph;

import java.util.*;

public class Matching {
	private static final String LEFT_VERT_PREFIX = "L_";
	private static final String RIGHT_VERT_PREFIX = "R_";

	public static HashMap<String, String> makeAssignments(List<String> membersList,
		List<PairTuple<String, String>> validPairings) {
		if (membersList.size() < 2) {
			return new HashMap<>();
		}

		MutableBipartiteGraph<String> mbg = MutableBipartiteGraph.create();

		Collections.shuffle(membersList, new Random(System.nanoTime()));
		for (String member : membersList) {
			mbg.insertLeftVertex(LEFT_VERT_PREFIX + member);
			mbg.insertRightVertex(RIGHT_VERT_PREFIX + member);
		}

		Collections.shuffle(validPairings, new Random(System.nanoTime()));
		for (PairTuple<String, String> pairing : validPairings) {
			mbg.addEdge(
				LEFT_VERT_PREFIX + pairing.first,
				RIGHT_VERT_PREFIX + pairing.second
			);
		}

		MaximumBipartiteMatchingResult<String> result = HopcroftKarpAlgorithm
			.getInstance()
			.calc(mbg);

		HashMap<String, String> assignments = new HashMap<>();
		for (String gifter : membersList) {
			String recipient = result
				.getMatchedVertex(LEFT_VERT_PREFIX + gifter)
				.replaceAll(RIGHT_VERT_PREFIX, "");
			assignments.put(gifter, recipient);
		}

		return assignments;
	}

	public static void printAssignments(HashMap<String, String> assignments) {
		if (assignments.isEmpty()) {
			return;
		}

		System.out.println("GIFTER,RECIPIENT");
		List<String> gifters = new ArrayList<>(assignments.keySet());
		Collections.sort(gifters);
		for (String gifter : gifters) {
			String recipient = assignments.get(gifter);
			System.out.println(gifter + "," + recipient);
		}
	}
}
