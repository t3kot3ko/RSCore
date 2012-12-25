package pullup;

public class PullUpMultiMethodsTest {

}

class PullUpMultiMethodsTestSubClass extends PullUpMultiMethodsTest {
	void m() {
	}

	public int a() {
		return 1;
	}
	private String s() {
		return "foo";
	}
}
