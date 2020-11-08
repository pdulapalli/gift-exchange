package giftexchange;

public class App {
	public static void main(String[] args) {
		Family fam = Family.loadFamilyInfo(System.in);
		Selection.computeExchanges(fam);
	}
}
