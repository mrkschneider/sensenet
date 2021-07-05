package com.tcb.sensenet.internal.task.export.file;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class WriteFileTaskConfig {
	public abstract Path getPath();
	public abstract List<String> getContent();
	
	public static WriteFileTaskConfig create(Path path, List<String> content){
		return new AutoValue_WriteFileTaskConfig(path,content);
	}
	
	public static WriteFileTaskConfig create(Path path, String content){
		return create(path,Arrays.asList(content));
	}
}
