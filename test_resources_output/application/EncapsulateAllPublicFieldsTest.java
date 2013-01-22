package application;

public class EncapsulateAllPublicFieldsTest {
	private int a;
	private String b;
	
	private int p;
	private String q;
	
	protected int x;
	protected String y;
	
	public static int aa;
	private static final int bb = 100;
	
	void m(){
		System.out.println(getA() + getB() + p + q + x + y + aa + bb);
	}
	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

}

class Callee{
	void m(){
		EncapsulateAllPublicFieldsTest e = new EncapsulateAllPublicFieldsTest();
		System.out.println(e.getA());
		System.out.println(e.getB());
		e.setA(100);
		e.setB("string");
		
		System.out.println(EncapsulateAllPublicFieldsTest.aa);
	}
}
