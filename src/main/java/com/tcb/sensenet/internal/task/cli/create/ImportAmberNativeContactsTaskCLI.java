package com.tcb.sensenet.internal.task.cli.create;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.amberImporter.AmberNativeContactsImporter;

public class ImportAmberNativeContactsTaskCLI extends AbstractImportTaskCLI {

	@Tunable(description="contacts file path")
	public String contactsPath;
	
	@Tunable(description="timeline series file path")
	public String timelinesPath;
	
	@Tunable(description="(optional) nonnative timeline series file path")
	public String nonnativeTimelinesPath = null;
	
	@Tunable(description="contacts pdb file path")
	public String pdbPath;
	
	public ImportAmberNativeContactsTaskCLI(AppGlobals appGlobals) {
		super(appGlobals);
	}

	@Override
	protected InteractionImporter getInteractionImporter() {
		List<Path> timelinePaths = new ArrayList<>();
		timelinePaths.add(Paths.get(timelinesPath));
		if(nonnativeTimelinesPath!=null){
			timelinePaths.add(Paths.get(nonnativeTimelinesPath));
		}
		return new AmberNativeContactsImporter(
				Paths.get(contactsPath),
				timelinePaths,
				Paths.get(pdbPath));
	}

}
