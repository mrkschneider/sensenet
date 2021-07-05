package com.tcb.sensenet.internal.meta.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Map;

import org.cytoscape.model.CyIdentifiable;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CySessionAdapter;

public final class SerializableSuidMap<V,T extends CyIdentifiable> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Map<Long, V> map;
	private Class<T> suidObjClass;

	public SerializableSuidMap(Map<Long,V> map, Class<T> suidObjClass){
		this.map = map;
		this.suidObjClass = suidObjClass;
	}
	
	public Map<Long,V> getMap(){
		return map;
	}
	
	private void readObject(ObjectInputStream in) throws  IOException, ClassNotFoundException{
		in.defaultReadObject();
		CySessionAdapter session = MetaObjectInputStream.create(in).session;
		SuidUpdater.update(map, session, suidObjClass);
	}
	
	
}
