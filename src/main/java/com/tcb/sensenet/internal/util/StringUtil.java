package com.tcb.sensenet.internal.util;

import java.util.List;
import java.util.stream.Collectors;

import com.tcb.common.util.ListFilter;

public class StringUtil {
	public static String toHTML(String s){
		return "<html><body>" + s + "</body></html>"; 
	}
	
	public static int getCommonStringLength(List<String> strings){
		return ListFilter.singleton(strings.stream()
				.map(s -> s.length())
				.collect(Collectors.toSet())).get();
	}
		
	
}
