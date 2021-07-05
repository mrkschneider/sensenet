package com.tcb.sensenet.internal.init.row;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StandardizedEdgeNameGenerator {
		
	public String getName(String sourceName, String targetName, String type, String bridgeName){
		if(!bridgeName.isEmpty()) return getName_(sourceName,targetName,type,bridgeName);
		List<String> names = Arrays.asList(sourceName,targetName)
				.stream()
				.sorted()
				.collect(Collectors.toList());
		return getName_(names.get(0),names.get(1),type,bridgeName);		
	}
	
	private String getName_(String sourceName, String targetName, String type, String bridgeName){
		String fullName = sourceName + "_" + bridgeName + "_" + targetName + "_" + type;
		return fullName;
	}

}
