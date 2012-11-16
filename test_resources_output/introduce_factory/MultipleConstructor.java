package introduce_factory;

public class MultipleConstructor {
	public static MultipleConstructor createMultipleConstructor() {
		return new MultipleConstructor();
	}
	public static MultipleConstructor createMultipleConstructor(int a) {
		return new MultipleConstructor(a);
	}
	public static MultipleConstructor createMultipleConstructor(int a, int b) {
		return new MultipleConstructor(a, b);
	}
	public static MultipleConstructor createMultipleConstructor(String s) {
		return new MultipleConstructor(s);
	}
	private MultipleConstructor(){}
	private MultipleConstructor(int a){}
	private MultipleConstructor(String s){}
	private MultipleConstructor(int a, int b){}
}
