package com.tcb.sensenet.internal.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.tcb.common.util.SafeMap;

public class ObjMap extends HashMap<Object,Object> {
	@SuppressWarnings("unchecked")
	public <T> T get(Object key, Class<T> type) {
		return (T) this.get(key);
	}
	
	public static ObjMap merge(ObjMap... maps) {
		ObjMap result = new ObjMap();
		for(ObjMap map:maps) {
			result.putAll(map);
		}
		return result;
	}
	
	@Override
	public Object get(Object key){
		if(!super.containsKey(key)) 
			throw new IllegalArgumentException(String.format("No object stored for: %s", key.toString()));
		Object value = super.get(key);
		return value;
	}
	
	public <T> T getOrDefault(Object key, T defaultValue, Class<T> type) {
		if(super.containsKey(key)) return this.get(key, type);
		else return defaultValue;
	} 
	
	public Object get(Object key, Supplier<Object> fac) {
		if(super.containsKey(key)) return this.get(key);
		else return fac.get();
	}
		
	@SuppressWarnings("unchecked")
	public <T> T get(Object key, Supplier<T> fac, Class<T> type){
		return (T) this.getOrDefault(key, fac);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(Object key, Class<T> clazz){
		return (List<T>) get(key);
	}
	
	@Override
	public Object put(Object key, Object value){
		if(super.containsKey(key))
			throw new IllegalArgumentException(String.format("Attempted override of key: %s", key.toString()));
		return super.put(key, value);			
	}
	
	public Object putOrReplace(Object key, Object value){
		return super.put(key, value);
	}
	
	@Override
	public void putAll(Map<?,?> o){
		o.entrySet().stream()
		.forEach(e -> put(e.getKey(), e.getValue()));
	}
	
	public void putOrReplaceAll(Map<?,?> o){
		super.putAll(o);
	}
	
	@Override
	public Object replace(Object key, Object value){
		get(key);
		return super.put(key, value);
	}
}
