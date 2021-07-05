package com.tcb.sensenet.internal.task.export.aif;

import java.nio.file.Path;


import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ExportAifTaskConfig {

	public static ExportAifTaskConfig create(Path path){
		return new AutoValue_ExportAifTaskConfig(path);
	}
	
	public abstract Path getPath();
}
