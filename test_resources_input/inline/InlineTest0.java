// Inline final && static field. Not remove the original declaration
package inline;

public class InlineTest0 {
	public InlineTest0(){}
	
	final static int FOO = 100;
	public int a = 200;

	public void m() {
		System.out.println(FOO);
	}

}
