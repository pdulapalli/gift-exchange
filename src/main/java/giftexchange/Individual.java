package giftexchange;

import java.util.Arrays;

public class Individual {
	private final String name;
	private final String[] parents;
	private final String spouse;

	public Individual(String name, String[] parents, String spouse) {
		this.name = name;
		this.parents = Arrays.stream(parents)
			.filter(x -> !x.isEmpty())
			.toArray(String[]::new);
		this.spouse = spouse;
	}

	public static Individual parseMember(String record) throws Exception {
		if (record == null) {
			throw new Exception("No record was supplied");
		}

		String[] contents = record.split(",", 4);
		if (contents.length < 3) {
			throw new Exception("Incomplete record was supplied");
		}

		String name = contents[0];
		String[] parents = Arrays.copyOfRange(contents, 1, 3);

		if (contents.length == 4) {
			return new Individual(name, parents, contents[3]);
		}

		return new Individual(name, parents, null);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Individual)) {
			return false;
		}

		Individual other = (Individual) obj;
		return this.name.equals(other.name);
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{")
			.append("name=")
			.append(this.name)
			.append(", parents=")
			.append(Arrays.toString(this.parents));
		if (spouse != null) {
			sb.append(", spouse=").append(this.spouse);
		}

		sb.append("}");

		return sb.toString();
	}

	public String getName() {
		return this.name;
	}

	public String getSpouse() {
		return this.spouse;
	}

	public String[] getParents() {
		return this.parents;
	}
}
