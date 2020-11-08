package giftexchange;

public class PairTuple<X, Y> {
	public final X first;
	public final Y second;

	public PairTuple(X first, Y second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public String toString() {
		return "(" + this.first + ", " + this.second + ")";
	}
}