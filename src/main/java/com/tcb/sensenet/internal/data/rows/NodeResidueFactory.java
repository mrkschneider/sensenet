package com.tcb.sensenet.internal.data.rows;

import org.cytoscape.model.CyNode;

import com.tcb.atoms.residues.Residue;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;


public class NodeResidueFactory {
	public Residue create(CyNode n, CyRootNetworkAdapter rootNetwork){
		CyRowAdapter row = rootNetwork.getRow(n);
		return create(row);
	}
	
	public Residue create(CyRowAdapter row){
		String chain = row.get(AppColumns.CHAIN, String.class);
		Integer resIndex = row.get(AppColumns.RESINDEX, Integer.class);
		String resName = row.get(AppColumns.RESNAME, String.class);
		String altLoc = row.get(AppColumns.ALTLOC, String.class);
		String resInsert = row.get(AppColumns.RESINSERT, String.class);
				
		Residue residue = Residue.create(resIndex, resName, resInsert, altLoc, chain);
		return residue;
	}
}
