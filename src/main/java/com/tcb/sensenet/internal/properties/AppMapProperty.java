package com.tcb.sensenet.internal.properties;

import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.tcb.common.util.SafeMap;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.ImportPanel;
import com.tcb.sensenet.internal.UI.panels.showInteractionsPanel.ShowInteractionsPanel;
import com.tcb.sensenet.internal.UI.panels.weightPanel.WeightPanel;

public enum AppMapProperty {
	UI_ACCORDEONS_OPEN;
	
	public static final char mapEntryDelimiter = ',';
	public static final char mapKeyValueDelimiter = '=';
	
	public Map<String,String> getDefaultValue(){
		switch(this){
		case UI_ACCORDEONS_OPEN: return getDefaultUIAccordeonsOpenMap();
		default: throw new IllegalArgumentException();
		}
	}
	
	public String getDefaultValueString(){
		return serialize(getDefaultValue());
	}
	
	private Map<String,String> getDefaultUIAccordeonsOpenMap(){
		Map<String,String> map = new SafeMap<String,String>();
		map.put(ImportPanel.class.getName(), Boolean.TRUE.toString());
		map.put(WeightPanel.class.getName(), Boolean.TRUE.toString());
		map.put(ShowInteractionsPanel.class.getName(), Boolean.TRUE.toString());
		return map;
	}
	
	public String serialize(Map<String,String> map){
		return Joiner
				.on(mapEntryDelimiter).
				withKeyValueSeparator(mapKeyValueDelimiter)
				.join(map);
	}
	
	public Map<String,String> deserialize(String s){
		if(s==null || s.isEmpty())	return new SafeMap<>();
		return Splitter
				.on(mapEntryDelimiter)
				.withKeyValueSeparator(mapKeyValueDelimiter)
				.split(s);
	}
}
