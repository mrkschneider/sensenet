package com.tcb.sensenet.internal.init;

import java.util.List;
import java.util.stream.Collectors;

public class NetworkImportNameGenerator {

	public String generateName(List<String> mainFileNames){
		if(mainFileNames.size() == 0) return "";
		return "(" + 
				mainFileNames.stream()
				.collect(Collectors.joining("+"))
				+ ")";
	}
	
	public String generateName(List<String> mainFileNames, List<String> differenceFileNames){
		if(mainFileNames.size() == 0 || differenceFileNames.size() == 0) return "";
		return generateName(mainFileNames) + "-" + generateName(differenceFileNames);
	}
		
}
