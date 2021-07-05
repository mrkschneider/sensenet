package com.tcb.sensenet.internal.util;

import java.awt.Component;
import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import javax.swing.JLabel;

import org.cytoscape.util.swing.FileChooserFilter;
import org.cytoscape.util.swing.FileUtil;

public class LoadFileUtil {
	
	public static Optional<File> loadFile(
			Component parent, FileUtil fileUtil,
			JLabel label,
			String fileDescription, String fileSuffix,
			String helpText, String fileNotLoadedLabel){
			FileChooserFilter filter = new FileChooserFilter(fileDescription, fileSuffix);
			Optional<File> fileOpt = Optional.ofNullable(fileUtil.getFile(parent, helpText,
					FileUtil.LOAD, Arrays.asList(filter)));
			if(fileOpt.isPresent())
				label.setText(fileOpt.get().getName());
			else
				label.setText(fileNotLoadedLabel);
			return fileOpt;
	}
		
}
