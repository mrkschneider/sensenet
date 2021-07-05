package com.tcb.sensenet.internal.task.cli.export;

import java.io.File;
import java.nio.file.Paths;

import org.cytoscape.task.TableTaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.AbstractWrappedTask;
import com.tcb.sensenet.internal.task.export.table.ExportEdgeTableTask;
import com.tcb.sensenet.internal.task.export.table.ExportNodeTableTask;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;
import com.tcb.cytoscape.cyLib.util.NullUtil;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.aifImporter.AifImporter;



public class ExportEdgeTableTaskCLI extends AbstractWrappedTask {

	@Tunable(description="out .csv file path")
	public String exportPath;
	
	public ExportEdgeTableTaskCLI(AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createWrappedTasks() {
		NullUtil.requireNonNull(exportPath, "exportPath");
		
		TaskIterator it = new TaskIterator();
		it.append(new ExportEdgeTableTask(exportPath,appGlobals));
		return it;
	}

}
