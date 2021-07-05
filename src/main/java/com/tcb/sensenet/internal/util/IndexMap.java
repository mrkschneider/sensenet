package com.tcb.sensenet.internal.util;

import java.util.List;

import com.tcb.common.util.SafeMap;

public class IndexMap {
	public static <T> SafeMap<T,Integer> create(List<T> lst){
		SafeMap<T,Integer> result = new SafeMap<>();
		for(int i=0;i<lst.size();i++){
			T ele = lst.get(i);
			result.put(ele, i);
		}
		return result;
	}
}
