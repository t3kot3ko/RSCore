package rename_field;

class RenamePrivateFields {
	public int a;
	protected int b;
	private int newC;
	
	public String aa;
	protected String bb;
	private String newCC;
	
	public int m1(){
		return newC;
	}
	public String m2(){
		return newCC;
	}
	
	public String m3(){
		return newCC + Integer.toString(newC);
	}
}