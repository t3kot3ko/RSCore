package introduce_factory;
public class DeclarationOnly2{
	public static DeclarationOnly2 createDeclarationOnly2(String s) {
		return new DeclarationOnly2(s);
	}

	private DeclarationOnly2(String s){}
}