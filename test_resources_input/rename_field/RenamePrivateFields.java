package rename_field;

class RenamePrivateFields {
	public int a;
	protected int b;
	private int c;
	
	public String aa;
	protected String bb;
	private String cc;
	
	public int m1(){
		return c;
	}
	public String m2(){
		return cc;
	}
	
	public String m3(){
		return cc + Integer.toString(c);
	}
}

