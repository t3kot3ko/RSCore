package rename_method;

public class RenamePrivateMethods {
	private void mm1(){
		
	}
	
	private void m2(){
		mm1();
		this.mm1();
		
		while(true){
			mm1();
		}
	}
	
	private int m3(){
		return 0;
	}


}
