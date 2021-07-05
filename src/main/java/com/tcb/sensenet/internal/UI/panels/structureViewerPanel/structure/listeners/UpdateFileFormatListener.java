package com.tcb.sensenet.internal.UI.panels.structureViewerPanel.structure.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Optional;

import javax.swing.JComboBox;

import org.apache.commons.io.FilenameUtils;

import com.tcb.sensenet.internal.UI.util.FileButton;
import com.tcb.netmap.fileFormat.FileFormat;
import com.tcb.netmap.fileFormat.FormatCollection;



public class UpdateFileFormatListener implements ActionListener {

	private FileButton button;
	private JComboBox<FileFormat> formatField;
	private FormatCollection formatCollection;

	public UpdateFileFormatListener(
			FileButton button,
			JComboBox<FileFormat> formatBox, 
			FormatCollection formatCollection){
		this.button = button;
		this.formatField = formatBox;
		this.formatCollection = formatCollection;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Optional<File> f = button.getMaybeFile();
		if(f.isPresent()){
			String extension = FilenameUtils.getExtension(f.get().getName());
			extension = extension.toLowerCase();
			FileFormat option = formatCollection.search(extension).orElse(null);
			formatField.setSelectedItem(option);
		} else {
			formatField.setSelectedItem(null);
		}
		
	}

}
