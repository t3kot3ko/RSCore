package application;

public class IntroduceParameterToComplicatedParametersTest {
	void m0(){
		
	}
	
	void m1(int a){
		System.out.println(a);
	}
	
	void m2(int a, int b){
		System.out.println(a + b);
	}
	
	void m3(M3Parameter parameterObject){
		System.out.println(parameterObject.a + parameterObject.b + parameterObject.c);
	}
	void m4(M4Parameter parameterObject){
		System.out.println(parameterObject.a + parameterObject.b + parameterObject.c + parameterObject.d);
	}
	void m5(M5Parameter parameterObject){
		System.out.println(parameterObject.a + parameterObject.b + parameterObject.c + parameterObject.d + parameterObject.e);
	}

}
