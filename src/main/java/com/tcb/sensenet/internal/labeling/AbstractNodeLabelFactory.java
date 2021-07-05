package com.tcb.sensenet.internal.labeling;

public abstract class AbstractNodeLabelFactory implements NodeLabelFactory {
	protected String baseFormat = "%1s%2s%3s-%4d";
	protected String atomFormatSuffix = ":%s";
	protected String groupFormatSuffix = "#%s";
	
	protected abstract String createBaseName(
			String chain, String altLoc,
			String resName, String resInsert, Integer resIndex);
	protected abstract String addMutatedResnameIfPresent(String name, String mutatedResname);
	protected abstract String addAtomName(String name, String atomName);
	protected abstract String addGroupTag(String name, String groupTag);
	
	public String createLabel(String chain, String altLoc,
			String resName, String resInsert,
			Integer resIndex, String mutatedResname,
			String atomName, String groupTag){
		String name = createBaseName(
				chain,
				altLoc,
				resName,
				resInsert,
				resIndex);
		
		name = addMutatedResnameIfPresent(name,mutatedResname);
		if(!atomName.isEmpty()){
			name = addAtomName(name,atomName);
		} else if(!groupTag.isEmpty()){
			name = addGroupTag(name,groupTag);
		}
		
		return name;
	}
		

}
