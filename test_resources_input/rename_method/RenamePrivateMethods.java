package rename_method;

public class RenamePrivateMethods {
	private void m1(){
		
	}
	
	private void m2(){
		m1();
		this.m1();
		
		while(true){
			m1();
		}
	}
	
	private int m3(){
		return 0;
	}


}

