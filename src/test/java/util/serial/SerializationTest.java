package util.serial;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.util.Sieve;

public class SerializationTest {
	
	private TestSerial obj;

	private String path = "/tmp/serial.ser";
	
	@Before
	public void setUp() throws Exception {
		this.obj = new TestSerial();
	}

	@Test
	public void testSerialize() {
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream(path);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(obj);
	         out.close();
	         fileOut.close();
	         
	         FileInputStream fileIn = new FileInputStream(path);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         Object o = (TestSerial)in.readObject();
	         
	         System.out.print(o);
	      } catch (IOException|ClassNotFoundException i) {
	         i.printStackTrace();
	      }
	}

	
}
