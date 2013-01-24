package application;

public class AddPrefixToPrivateFieldsTest {
	public int a;
	private int _b;
	protected int c;
	int d;
	
	void m(){
		System.out.println(a + _b + c + d);
	}

}

class Foo{
	public int a;
	private int _b;
	protected int c;
	int d;
	
	void m(){
		System.out.println(a + _b + c + d);
	}
}

class Bar{
	public int a;
	private int _b;
	protected int c;
	int d;
	
	void m(){
		System.out.println(a + _b + c + d);
	}
}

class Hoge{
	public static int a;
	private static int b;
	protected static int c;
	static int d;
	void m(){
		System.out.println(a + b + c + d);
	}
}
