package application_introduce_factory;

public abstract class AbstractDescriptor {
	public static AbstractDescriptor for1() {
		return new Descriptor1();
	}
	public static AbstractDescriptor for2() {
		return new Descriptor2();
	}
	public static AbstractDescriptor for3() {
		return new Descriptor3();
	}



	protected AbstractDescriptor(){}
}
