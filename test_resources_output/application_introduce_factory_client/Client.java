package application_introduce_factory_client;

import java.util.ArrayList;
import java.util.List;

import application_introduce_factory.AbstractDescriptor;
import application_introduce_factory.Descriptor1;
import application_introduce_factory.Descriptor2;
import application_introduce_factory.Descriptor3;

public class Client {
	void m(){
		List<AbstractDescriptor> list = new ArrayList<AbstractDescriptor>();
		list.add(AbstractDescriptor.for1());
		list.add(AbstractDescriptor.for2());
		list.add(AbstractDescriptor.for3());
	}

}
