package introduce_factory;

public class DeclarationAndCaller {
	public static DeclarationAndCaller createDeclarationAndCaller() {
		return new DeclarationAndCaller();
	}

	private DeclarationAndCaller(){}
}

class Foo{
	public void m(){
		DeclarationAndCaller d = DeclarationAndCaller.createDeclarationAndCaller();
	}
}
