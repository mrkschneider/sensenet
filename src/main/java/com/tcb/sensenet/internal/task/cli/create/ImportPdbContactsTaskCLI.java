package com.tcb.sensenet.internal.task.cli.create;

import java.nio.file.Paths;

import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.cytoscape.cyLib.util.NullUtil;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.pdbImporter.PdbContactImporter;



public class ImportPdbContactsTaskCLI extends AbstractImportTaskCLI {

	@Tunable(description="pdb file path")
	public String pdbPath;
	
	@Tunable(description="distance cutoff")
	public Double distanceCutoff;
	
	@Tunable(description="atom pattern")
	public String atomPattern;
	
	public ImportPdbContactsTaskCLI(AppGlobals appGlobals) {
		super(appGlobals);
	}

	@Override
	protected InteractionImporter getInteractionImporter() {
		NullUtil.requireNonNull(distanceCutoff, "distance cutoff");
		NullUtil.requireNonNull(atomPattern, "atom pattern");
		
		return new PdbContactImporter(
				Paths.get(pdbPath),
				distanceCutoff,
				atomPattern);	
	}

}
