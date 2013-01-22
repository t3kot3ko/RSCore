package application;

public class EncapsulateAllPublicFieldsTest {
	public int a;
	public String b;
	
	private int p;
	private String q;
	
	protected int x;
	protected String y;
	
	public static int aa;
	private static final int bb = 100;
	
	void m(){
		System.out.println(a + b + p + q + x + y + aa + bb);
	}
}

class Callee{
	void m(){
		EncapsulateAllPublicFieldsTest e = new EncapsulateAllPublicFieldsTest();
		System.out.println(e.a);
		System.out.println(e.b);
		e.a = 100;
		e.b = "string";
		
		System.out.println(EncapsulateAllPublicFieldsTest.aa);
	}
}
