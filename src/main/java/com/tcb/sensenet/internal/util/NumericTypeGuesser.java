package com.tcb.sensenet.internal.util;

import java.util.ArrayList;
import java.util.List;

public class NumericTypeGuesser {
	public Object guess(String s){
		try{
			Long i = Long.parseLong(s);
			return i;
		} catch(Exception e) {};
		
		try{
			Double d = Double.parseDouble(s);
			return d;
		} catch(Exception e) {};
		
		return s;
	} 
		
}
