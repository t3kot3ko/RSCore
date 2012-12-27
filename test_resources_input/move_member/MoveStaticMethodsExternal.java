package move_member;

public class MoveStaticMethodsExternal {
	public static void m1(){
		
	}
	private static void m2(){
		
	}
	
	public void instanceMethod(){
		
	}

}

class Foo{
	void m(){
		MoveStaticMethodsExternal.m1();
	}
}



