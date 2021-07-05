package com.tcb.sensenet.internal.labeling.examples;

import com.tcb.sensenet.internal.labeling.LabelSettings;
import com.tcb.sensenet.internal.labeling.ShortNodeLabelFactory;

public class ExampleShortNodeLabelFactory {

	private String chain = "A";
	private String altLoc = "";
	private String resName = "ARG";
	private String resInsert = "";
	private Integer resIndex = 100;
	private String atomName = "N";
	private String groupTag = "";
	private String mutatedResname = "";
	private ShortNodeLabelFactory factory;
		
	public ExampleShortNodeLabelFactory(LabelSettings settings){
		this.factory = new ShortNodeLabelFactory(settings);
	}
	
	public String createAtomExampleLabel(){
		return factory.createLabel(
				chain, altLoc, resName, resInsert, resIndex, mutatedResname, atomName, groupTag);
	}
	
	public String createResidueExampleLabel(){
		return factory.createLabel(
				chain, altLoc, resName, resInsert, resIndex, mutatedResname, "", groupTag);
	}
	
	
}
