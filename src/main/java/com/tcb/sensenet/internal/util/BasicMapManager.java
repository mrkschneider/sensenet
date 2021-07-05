package com.tcb.sensenet.internal.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.cytoscape.model.CyNetwork;

import java.util.Set;

import com.tcb.sensenet.internal.meta.serialization.MetaObjectInputStream;
import com.tcb.sensenet.internal.meta.serialization.SuidUpdater;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CySessionAdapter;
import com.tcb.common.util.SafeMap;

public abstract class BasicMapManager<K,V,T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected abstract K getKey(T obj);
			
	private SafeMap<K,V> map;
	
	public BasicMapManager(){
		reset();
	}
				
	public V get(T obj){
		K key = getKey(obj);
		return map.get(key);
	}
	
	public V getOrDefault(T obj, V defaultValue){
		K key = getKey(obj);
		return map.getOrDefault(key, defaultValue);
	}
	
	public void put(T obj, V value){
		K key = getKey(obj);
		map.put(key, value);
	}
	
	public void putOrReplace(T obj, V value){
		K key = getKey(obj);
		map.putOrReplace(key, value);
	}
	
	public Set<Entry<K,V>> entrySet(){
		return map.entrySet();
	}
	
	public Map<K,V> getData(){
		return map;
	}
	
	public void putAll(BasicMapManager<K,V,T> o){
		putAll(o.map);		
	}
	
	public void putAll(Map<K,V> otherMap){
		map.putAll(otherMap);
	}
			
	public void remove(T obj){
		K key = getKey(obj);
		map.remove(key);
	}
	
	public void reset(){
		this.map = new SafeMap<>();
	}
	
	public Boolean containsKey(T obj){
		K key = getKey(obj);
		return map.containsKey(key);
	}
	
	public void setData(SafeMap<K,V> newMap){
		map = newMap;
	}
	
	public Collection<V> values(){
		return map.values();
	}
}
