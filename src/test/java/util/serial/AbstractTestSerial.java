package util.serial;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class AbstractTestSerial implements Serializable {
	
	protected String label;

	public AbstractTestSerial(String label){
		this.label = label;
	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException{
		in.defaultReadObject();
		System.out.println("Reading");
	}
}
