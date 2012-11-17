package introduce_factory;

public class MultipleConstructor {
	public static MultipleConstructor createMultipleConstructor() {
		return new MultipleConstructor();
	}
	public static MultipleConstructor createMultipleConstructor(int s) {
		return new MultipleConstructor(s);
	}
	public static MultipleConstructor createMultipleConstructor(String s) {
		return new MultipleConstructor(s);
	}
	public static MultipleConstructor createMultipleConstructor(int a, String s) {
		return new MultipleConstructor(a, s);
	}
	private MultipleConstructor() {}
	private MultipleConstructor(int s) {}
	private MultipleConstructor(String s) {}
	private MultipleConstructor(int a, String s) {}
}
