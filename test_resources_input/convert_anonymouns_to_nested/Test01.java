package convert_anonymouns_to_nested;

public class Test01 {
	private static final class Inner extends Test01 {
	}

	void f(){
		new Inner();
	}
}
