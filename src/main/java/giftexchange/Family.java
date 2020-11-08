package giftexchange;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Family {
	private final List<Individual> members;
	private final Hashtable<String, Individual> lookupTable;

	public Family(List<Individual> members) {
		this.members = members;
		this.lookupTable = buildLookupTable(members);
	}

	public static Family loadFamilyInfo(InputStream src) {
		Scanner in = new Scanner(src);
		List<Individual> lst = new ArrayList<>();

		while (in.hasNextLine()) {
			String currentLine = in.nextLine();
			try {
				Individual parsed = Individual.parseMember(currentLine);
				lst.add(parsed);
			} catch (Exception ex) {
				System.err.println("Unable to process line: " + ex);
			}
		}

		return new Family(lst);
	}

	private Hashtable<String, Individual> buildLookupTable(List<Individual> familyMembers) {
		Hashtable<String, Individual> ht = new Hashtable<>();
		for (Individual ind : familyMembers) {
			ht.put(ind.getName(), ind);
		}

		return ht;
	}

	public List<Individual> getMembers() {
		return this.members;
	}

	public List<String> getMemberNames() {
		return this.members
			.stream()
			.map(Individual::getName)
			.collect(Collectors.toList());
	}

	public Hashtable<String, Individual> getLookupTable() {
		return this.lookupTable;
	}
}
