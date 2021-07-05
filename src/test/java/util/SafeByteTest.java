package util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tcb.sensenet.internal.util.SafeByte;

public class SafeByteTest {

	@Test
	public void testCorrectByteValue() {
		int correctByte  = 4;
		Byte refByte = 0x0004;
		
		assertEquals(refByte,SafeByte.byteValue(correctByte));
	}
	
	@Test(expected=RuntimeException.class)
	public void testOverflowByteValue() {
		int overflowByte = 128;
		
		SafeByte.byteValue(overflowByte);
	}
	
	@Test(expected=RuntimeException.class)
	public void testUnderflowByteValue() {
		int underflowByte = -129;
		SafeByte.byteValue(underflowByte);
	}

}
