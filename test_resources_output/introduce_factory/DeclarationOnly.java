package introduce_factory;
public class DeclarationOnly{
	public static DeclarationOnly createDeclarationOnly() {
		return new DeclarationOnly();
	}
	private DeclarationOnly(){}
}