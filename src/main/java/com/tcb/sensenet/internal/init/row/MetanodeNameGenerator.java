package com.tcb.sensenet.internal.init.row;


import com.tcb.atoms.atoms.Atom;
import com.tcb.atoms.residues.Residue;
import com.tcb.aifgen.importer.InteractionImportData;
import com.tcb.common.util.Tuple;



public class MetanodeNameGenerator {

	private String groupTag;
	private NodeNameGenerator nameGenerator;
	
	public MetanodeNameGenerator(String groupTag){
		this.groupTag = groupTag;
		this.nameGenerator = new NodeNameGenerator();
	}
	
	public String getName(Residue residue, InteractionImportData importer){
		String base = nameGenerator.getFullResName(residue, importer);
		if(groupTag.equals("")) return base;
		return String.format("%s#%s", groupTag, base);
	}
	
	public String getMutationName(Residue residue, InteractionImportData importer){
		return nameGenerator.getMutationName(residue, importer);
	}
	
}
