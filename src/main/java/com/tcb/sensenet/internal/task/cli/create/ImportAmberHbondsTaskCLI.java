package com.tcb.sensenet.internal.task.cli.create;

import java.nio.file.Paths;

import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.amberImporter.AmberHbondImporter;


public class ImportAmberHbondsTaskCLI extends AbstractImportTaskCLI {

	@Tunable(description="hbonds file path")
	public String hbondsPath;
	
	@Tunable(description="timeline file path")
	public String timelinesPath;
		
	public ImportAmberHbondsTaskCLI(AppGlobals appGlobals) {
		super(appGlobals);
	}

	@Override
	protected InteractionImporter getInteractionImporter() {
		return new AmberHbondImporter(Paths.get(hbondsPath),
				Paths.get(timelinesPath));
	}

}
