package com.tcb.sensenet.internal.task.export.table.factories;

import java.io.File;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.task.write.ExportTableTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;

public class ExportTableTaskFactoryAdapter {
	private ExportTableTaskFactory fac;

	public ExportTableTaskFactoryAdapter(
			ExportTableTaskFactory fac){
		this.fac = fac;
	}
	
	public TaskIterator createTaskIterator(CyTableAdapter table, File file){
		return fac.createTaskIterator(table.getAdaptedTable(), file);
	}
}
