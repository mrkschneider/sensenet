package com.tcb.sensenet.internal.data.rows;

import org.cytoscape.model.CyNode;

import com.tcb.atoms.atoms.Atom;
import com.tcb.atoms.residues.Residue;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;


public class NodeAtomFactory {
	public Atom create(CyNode n, CyRootNetworkAdapter rootNetwork){
		CyRowAdapter row = rootNetwork.getRow(n);
		return create(row);
	}
	
	public Atom create(CyRowAdapter row){
		Residue residue = new NodeResidueFactory().create(row);
		String atomName = row.get(AppColumns.ATOM_NAME, String.class);
		Atom atom = Atom.create(atomName, residue);
		return atom;
	}
}
