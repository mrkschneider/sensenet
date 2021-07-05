package com.tcb.sensenet.internal.util;

import java.util.List;
import java.util.stream.Collectors;

public class ObjectPrinter {
	public String toString(Object o){
		if(o instanceof List){
			List<?> lst = (List<?>)o;
			return lst.stream()
					.map(e -> e.toString())
					.collect(Collectors.joining(" "));
		} else {
			return o.toString();
		}
	}
}
