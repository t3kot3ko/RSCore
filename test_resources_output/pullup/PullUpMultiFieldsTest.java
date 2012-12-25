package pullup;

public class PullUpMultiFieldsTest {
	public int a = 100;
	private int b = 200;
	protected String s = "aaa";
	double d = 1.2;
}

class PullUpMultiFieldsTestSubClass extends PullUpMultiFieldsTest{
}
