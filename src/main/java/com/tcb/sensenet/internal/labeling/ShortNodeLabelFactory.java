package com.tcb.sensenet.internal.labeling;

import com.tcb.aifgen.util.ResidueNameUtil;

public class ShortNodeLabelFactory extends AbstractNodeLabelFactory {
	private LabelSettings settings;

	public ShortNodeLabelFactory(LabelSettings settings){
		this.settings = settings;		
	}
	
	@Override
	public String createBaseName(String chain, String altLoc, String resName, String resInsert, Integer resIndex){
		String name = chain + "/";
		if(settings.removeChainIds || chain.isEmpty()){
			name = "";
		}
		
		if(settings.useShortNames){
			resName = ResidueNameUtil.threeToOneLetter(resName);
		}
		
		if(!settings.useNames) {
			resName = "";
		}
		
		name += altLoc + resName;
		
		String dash = "-";
		if(settings.removeDashes){
			dash = "";
		}
		
		name += dash + resIndex + resInsert;
						
		return name;
	}
	
	@Override
	protected String addMutatedResnameIfPresent(String name, String mutatedResname){
		if(!mutatedResname.isEmpty()){
			if(settings.useShortNames)
				mutatedResname = ResidueNameUtil.threeToOneLetter(mutatedResname);
			String dash = "-";
			if(settings.removeDashes)
				dash = "";
			name += dash + mutatedResname;
		}
		return name;
	}
	
	@Override
	protected String addAtomName(String name, String atomName){
		name += String.format(atomFormatSuffix, atomName);
		return name;
	}
	
	@Override
	protected String addGroupTag(String name, String groupTag){
		name += String.format(groupFormatSuffix, groupTag);
		return name;
	}
	
	
	
}
