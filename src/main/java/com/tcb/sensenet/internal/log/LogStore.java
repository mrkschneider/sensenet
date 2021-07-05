package com.tcb.sensenet.internal.log;


import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.common.util.SafeMap;

public class LogStore extends SafeMap<String,LogBuilder> {

	@Override
	public LogBuilder get(Object key){
		if(key.getClass().isEnum()){
			Enum k = (Enum)key;
			return super.get(k.name());
		} else {
			return get(key);
		}
	}
	
	@Override
	public boolean containsKey(Object key){
		if(key.getClass().isEnum()){
			Enum k = (Enum)key;
			return super.containsKey(k.name());
		}
		return super.containsKey(key);
	}
	
}
