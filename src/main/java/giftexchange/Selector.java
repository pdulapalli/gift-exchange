package giftexchange;

import org.psjava.algo.graph.matching.HopcroftKarpAlgorithm;
import org.psjava.algo.graph.matching.MaximumBipartiteMatchingResult;
import org.psjava.ds.graph.MutableBipartiteGraph;

import java.util.*;

public class Selector {
	private static final String LEFT_VERT_PREFIX = "L_";
	private static final String RIGHT_VERT_PREFIX = "R_";

	public static List<PairTuple<String, String>> makeAssignments(List<String> membersList,
		List<PairTuple<String, String>> validPairings) {
		if (membersList.size() < 2) {
			return new ArrayList<>();
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

		List<PairTuple<String, String>> assignments = new ArrayList<>();
		for (String gifter : membersList) {
			String recipient = result
				.getMatchedVertex(LEFT_VERT_PREFIX + gifter)
				.replaceAll(RIGHT_VERT_PREFIX, "");
			assignments.add(new PairTuple<>(gifter, recipient));
		}

		return assignments;
	}

	public static void printAssignments(List<PairTuple<String, String>> assignments) {
		if (assignments.isEmpty()) {
			return;
		}

		System.out.println("GIFTER,RECIPIENT");
		assignments.sort(Comparator.comparing(a -> a.first));
		for (PairTuple<String, String> pairing : assignments) {
			String gifter = pairing.first;
			String recipient = pairing.second;
			System.out.println(gifter + "," + recipient);
		}
	}
}
