package com.tcb.sensenet.internal.task.cli.create;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.importer.AutoExtensionAifImporterFactory;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.aifImporter.AifImporter;
import com.tcb.aifgen.importer.aifImporter.ZaifImporter;



public class ImportAifTaskCLI extends AbstractImportTaskCLI {

	@Tunable(description=".aif file path")
	public String importPath;
	
	@Tunable(description="sieve frames (default 1)")
	public Integer skipFrames = 1;
	
	@Tunable(description="minimum timeline average (default -Inf)")
	public Double minAvg = Double.NEGATIVE_INFINITY;
	
	public ImportAifTaskCLI(AppGlobals appGlobals) {
		super(appGlobals);
	}

	@Override
	protected InteractionImporter getInteractionImporter() {
		return new AutoExtensionAifImporterFactory().create(importPath,skipFrames,minAvg);
	}

}
