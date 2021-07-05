package com.tcb.sensenet.internal.meta.serialization;

import java.util.Map;
import java.util.Map.Entry;

import org.cytoscape.model.CyIdentifiable;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CySessionAdapter;
import com.tcb.common.util.SafeMap;

public class SuidUpdater {
	public static <V,T extends CyIdentifiable> void update(Map<Long,V> map, CySessionAdapter session, Class<T> clazz){
		SafeMap<Long,V> result = new SafeMap<>();
		for(Entry<Long,V> entry:map.entrySet()){
			Long oldKey = entry.getKey();
			V value = entry.getValue();
			Long key = null;
			try{
				key = session.getUpdatedSUID(oldKey, clazz);
			} catch(NullPointerException e){
				System.out.println(String.format(
						"Warning: Could not find new SUID for %s: %d",clazz, oldKey));
				continue;
			}
			result.put(key, value);
		}
		map.clear();
		map.putAll(result);
	}
}
