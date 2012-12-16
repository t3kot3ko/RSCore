package detail_test;

public class StatementsCount {
	void m() {
		System.out.println("Hello world");
		int a = 10;
		int j;

		try {
			n();

		} catch (Exception e) {
			System.out.println("e");
		}

		for (int i = 0; i < 100; i++) {
			System.out.println(i);
		}
	}

	void n() {
	}

}
