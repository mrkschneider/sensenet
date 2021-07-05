package com.tcb.sensenet.internal.importer;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;

import com.tcb.aifgen.importer.aifImporter.AifImporter;
import com.tcb.aifgen.importer.aifImporter.ZaifImporter;

public class AutoExtensionAifImporterFactory {
	public AifImporter create(String path, Integer skipFrames, Double minAvg){
		String extension = FilenameUtils.getExtension(path.toString());
		Path p = Paths.get(path);
		if(extension.equals("zaif")) {
			return new ZaifImporter(p,skipFrames,minAvg); 
		} else {
			return new AifImporter(p,skipFrames,minAvg);
		}
	}
}
