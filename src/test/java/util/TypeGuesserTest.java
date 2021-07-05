package util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.util.NumericTypeGuesser;

public class TypeGuesserTest {

	private String intStr;
	private long longRef;
	private String longStr;
	private String doubleStr;
	private double doubleRef;
	private String strStr;
	private NumericTypeGuesser test;

	@Before
	public void setUp() throws Exception {
		this.longStr = "5";
		this.longRef = 5;
		this.doubleStr = "5.1";
		this.doubleRef = 5.1;
		this.strStr = "abc";
		this.test = new NumericTypeGuesser();
		
	}

	@Test
	public void testGuessTypeLong() {
		assertEquals(longRef, test.guess(longStr));
	}
	
	@Test
	public void testGuessTypeDouble() {
		assertEquals(doubleRef, test.guess(doubleStr));
	}
	
	@Test
	public void testGuessTypeString() {
		assertEquals(strStr, test.guess(strStr));
	}

}
