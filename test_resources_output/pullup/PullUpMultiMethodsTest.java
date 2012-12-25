package pullup;

public class PullUpMultiMethodsTest {

	void m() {
	}

	public int a() {
		return 1;
	}

	private String s() {
		return "foo";
	}

}

class PullUpMultiMethodsTestSubClass extends PullUpMultiMethodsTest {
}
