package com.tcb.sensenet.internal.task.export.matrix;

import java.nio.file.Path;


import com.google.auto.value.AutoValue;
import com.tcb.cytoscape.cyLib.data.Columns;

@AutoValue
public abstract class ExportNetworkMatrixTaskConfig {
	
	public abstract Path getFilePath();
	public abstract String getWeightColumn();
	public abstract String getNodeNameColumn();
	
	
	
	public static ExportNetworkMatrixTaskConfig create(Path path, String weightColumn, String nodeNameColumn){
		return new AutoValue_ExportNetworkMatrixTaskConfig(path,weightColumn,nodeNameColumn);
	}
}
