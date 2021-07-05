package util.serial;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class TestSerial extends AbstractTestSerial implements Serializable {
	private String content;

	public TestSerial(){
		super("lab");
		this.content = "abc";
	}
	
	@Override
	public String toString(){
		return String.format("%s %s", label, content);
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		System.out.println("Reading subclass");
}
}
