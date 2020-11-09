package giftexchange;

import java.util.*;
import java.util.function.Predicate;

public class Validator {
	private final Hashtable<String, Individual> lookupTable;

	public Validator(Hashtable<String, Individual> lookupTable) {
		this.lookupTable = lookupTable;
	}

	public List<PairTuple<String, String>> getValidPairings(Predicate<PairTuple<String, String>> pairingValidityCheckFunction) {
		List<Individual> peopleSetA = new ArrayList<>(lookupTable.values());
		List<Individual> peopleSetB = new ArrayList<>(peopleSetA);

		Collections.shuffle(peopleSetA, new Random(System.nanoTime()));
		Collections.shuffle(peopleSetB, new Random(System.nanoTime()));

		List<PairTuple<String, String>> validPairings = new ArrayList<>();
		for (Individual firstPerson : peopleSetA) {
			for (Individual secondPerson : peopleSetB) {
				PairTuple<String, String> pairing =
					new PairTuple<>(firstPerson.getName(),
						secondPerson.getName());

				if (pairingValidityCheckFunction.test(pairing)) {
					validPairings.add(pairing);
				}
			}
		}

		return validPairings;
	}

	public boolean notPairedToSelf(PairTuple<String, String> pairing) {
		return pairing.first != null &&
			   !pairing.first.equals(pairing.second);
	}

	public boolean notPairedToImmediateFamily(PairTuple<String, String> pairing) {
		if (!this.lookupTable.containsKey(pairing.first) ||
			!this.lookupTable.containsKey(pairing.second)) {
			return false;
		}

		Individual firstPerson = this.lookupTable.get(pairing.first);
		Individual secondPerson = this.lookupTable.get(pairing.second);

		return !firstPerson.isImmediateFamilyMember(secondPerson);
	}
}
