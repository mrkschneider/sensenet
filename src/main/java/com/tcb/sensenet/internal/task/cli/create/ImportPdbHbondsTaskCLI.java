package com.tcb.sensenet.internal.task.cli.create;

import java.nio.file.Paths;

import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.cytoscape.cyLib.util.NullUtil;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.pdbImporter.PdbHbondImporter;


public class ImportPdbHbondsTaskCLI extends AbstractImportTaskCLI {

	@Tunable(description="pdb file path")
	public String pdbPath;
	
	@Tunable(description="distance cutoff")
	public Double distanceCutoff;
	
	@Tunable(description="angle cutoff")
	public Double angleCutoff;
	
	@Tunable(description="donor pattern")
	public String donorPattern;
	
	@Tunable(description="acceptor pattern")
	public String acceptorPattern;
	
	
	public ImportPdbHbondsTaskCLI(AppGlobals appGlobals) {
		super(appGlobals);
	}

	@Override
	protected InteractionImporter getInteractionImporter() {
		NullUtil.requireNonNull(distanceCutoff, "distance cutoff");
		NullUtil.requireNonNull(angleCutoff, "angle cutoff");
		NullUtil.requireNonNull(donorPattern, "donor pattern");
		NullUtil.requireNonNull(acceptorPattern, "acceptor pattern");
		
		return new PdbHbondImporter(
				Paths.get(pdbPath),
				distanceCutoff,
				angleCutoff,
				donorPattern,
				acceptorPattern);	
	}

}
