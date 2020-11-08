package giftexchange;

import org.psjava.algo.graph.matching.HopcroftKarpAlgorithm;
import org.psjava.algo.graph.matching.MaximumBipartiteMatchingResult;
import org.psjava.ds.graph.MutableBipartiteGraph;

import java.util.*;

public class Selector {
	private static final String LEFT_VERT_PREFIX = "L_";
	private static final String RIGHT_VERT_PREFIX = "R_";

	public static HashMap<String, String> makeAssignments(Family fm) {
		List<String> membersList = new ArrayList<>(fm.getMemberNames());
		if (membersList.size() < 2) {
			return new HashMap<>();
		}

		Collections.shuffle(membersList);
		MutableBipartiteGraph<String> mbg = weaveMatcherGraph(membersList);

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

	private static MutableBipartiteGraph<String> weaveMatcherGraph(List<String> membersList) {
		MutableBipartiteGraph<String> mbg = MutableBipartiteGraph.create();
		for (String gifter : membersList) {
			mbg.insertLeftVertex(LEFT_VERT_PREFIX + gifter);
			mbg.insertRightVertex(RIGHT_VERT_PREFIX + gifter);
		}

		for (String gifter : membersList) {
			for (String possibleRecipient : membersList) {
				if (isValidPairing(gifter, possibleRecipient)) {
					mbg.addEdge(
						LEFT_VERT_PREFIX + gifter,
						RIGHT_VERT_PREFIX + possibleRecipient
					);
				}
			}
		}

		return mbg;
	}

	private static boolean isValidPairing(String elA, String elB) {
		return !elA.equals(elB);
	}

	public static void printAssignments(HashMap<String, String> assignments) {
		if (assignments.isEmpty()) {
			return;
		}

		System.out.println("GIFTER -> RECIPIENT");
		for (Map.Entry<String, String> entry : assignments.entrySet()) {
			String gifter = entry.getKey();
			String recipient = entry.getValue();
			System.out.println(gifter + " -> " + recipient);
		}
	}
}
