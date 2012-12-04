package rename_method;

public class RenamePublicMethods {
	public void mm1(){
		
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

