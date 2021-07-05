package com.tcb.sensenet.internal.init.row;


import com.tcb.atoms.atoms.Atom;
import com.tcb.atoms.residues.Residue;
import com.tcb.aifgen.importer.InteractionImportData;
import com.tcb.common.util.Tuple;



public class NodeNameGenerator {

	private static final String residueFormat = "%s/%s%s%s-%d";
		
	public String getFullResName(Residue residue, InteractionImportData importer){
		Tuple<String,String> mutation = getMutation(residue, importer);
		String resName = residue.getName();
		String suffix = "";
		if(mutation!=null){
			resName = mutation.one();
			suffix = "-" + mutation.two();
		}
		String prefix = String.format(residueFormat, 
				residue.getChain(),
				residue.getAltLoc(),
				resName,
				residue.getResidueInsert(),
				residue.getIndex()
				);
		String fullResName = prefix + suffix;
		return fullResName;
	}
	
	private Tuple<String,String> getMutation(Residue residue, 
			InteractionImportData importer){
		return importer.getMutationMap()
				.getOrDefault(residue, null);
	}
	
	public String getName(Atom atom, InteractionImportData importer){
		Residue residue = atom.getResidue();
		String resPrefix = getFullResName(residue, importer);
		return String.format("%s:%s", 
				resPrefix,
				atom.getName());
	}
	
	public String getMutationName(Residue residue, InteractionImportData importer){
		Tuple<String,String> mutation = getMutation(residue, importer);
		String mutatedResName = "";
		if(mutation!=null){
			mutatedResName = mutation.two();
		}
		return mutatedResName;
	}
}
