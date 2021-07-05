package com.tcb.sensenet.internal.properties;

import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import org.apache.commons.lang3.EnumUtils;

import com.tcb.common.util.SafeMap;

public class AppProperties {
	private Properties properties;
	
	

	public AppProperties(Properties properties){
		this.properties = properties;
		verifyAllPropertiesHaveDefaultValues();
	}
	
	private void verifyAllPropertiesHaveDefaultValues(){
		for(AppProperty t:AppProperty.values()){
			assert(t.getDefaultValue()!=null);
		}
	}
	
	public String getOrDefault(AppProperty type){
		String key = type.name();
		String result = properties.getProperty(key, type.getDefaultValue());
		if(result.isEmpty()) result = type.getDefaultValue();
		return result;
	}
	
	public <T extends Enum<T>> T getEnumOrDefault(Class<T> clazz, AppProperty type){
		String propertyValue = getOrDefault(type);
		T result = (T) EnumUtils.getEnum(clazz, propertyValue);
		if(result == null) result = (T) EnumUtils.getEnum(clazz,type.getDefaultValue());
		if(result == null) throw new IllegalArgumentException("Could not find default value: " + type.name());
		return result;
	}
	
	public String getOrNull(AppProperty type){
		return properties.getProperty(type.name());
	}
	
	public <T> Map<String,T> getMapOrDefault(AppMapProperty type, Function<String,T> parser){
		String key = type.name();
		String result = properties.getProperty(key, type.getDefaultValueString());
		Map<String,String> rawMap = type.deserialize(result);		
		Map<String,T> map = new SafeMap<String,T>();
		for(String k:rawMap.keySet()){
			T value = parser.apply(rawMap.get(k));
			map.put(k, value);
		}
		return map;
	}
	
	public void setMap(AppMapProperty type, Map<String,String> map){
		String key = type.name();
		if(map==null){
			properties.remove(key);
		} else {
			String s = type.serialize(map);
			properties.setProperty(type.name(), s);
		}
	}
	
	public void set(AppProperty type, String value){
		String key = type.name();
		if(value==null) {
			properties.remove(key);
		} else {
			properties.setProperty(key, value);
		}
	}
	
	public void resetDefaults(){
		for(AppProperty p:AppProperty.values()){
			this.set(p, p.getDefaultValue());
		}
	}
}
