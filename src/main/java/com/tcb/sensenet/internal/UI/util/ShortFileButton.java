package com.tcb.sensenet.internal.UI.util;

import java.io.File;

import org.cytoscape.util.swing.FileUtil;

public class ShortFileButton extends FileButton {

	public ShortFileButton(
			String startFile,
			String defaultLabel,
			String fileDescription,
			String[] fileSuffixes,
			String helpText,
			FileUtil fileUtil) {
		super(startFile, defaultLabel, fileDescription, fileSuffixes, helpText, fileUtil);
	}
	
	public ShortFileButton(
			String defaultLabel,
			String fileDescription,
			String[] fileSuffixes,
			String helpText,
			FileUtil fileUtil) {
		super(defaultLabel, fileDescription, fileSuffixes, helpText, fileUtil);
	}

	@Override
	protected String getShortFileLabel(File f){
		return f.getName();
	}
}
