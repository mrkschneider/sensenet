package com.tcb.sensenet.internal.UI.panels.structureViewerPanel.structure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.UI.panels.structureViewerPanel.structure.listeners.UpdateFileFormatListener;
import com.tcb.sensenet.internal.UI.util.FileButton;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.structureViewer.StructureViewerManager;
import com.tcb.sensenet.internal.structureViewer.ViewerType;
import com.tcb.sensenet.internal.task.structureViewer.config.SingleStructureLoader;
import com.tcb.sensenet.internal.task.structureViewer.config.StructureLoader;
import com.tcb.netmap.fileFormat.FileFormat;
import com.tcb.netmap.fileFormat.FormatCollection;


public class SingleStructureImportPanel extends AbstractStructureImportPanel {
	private FileButton structureFileButton;
	private JComboBox<FileFormat> structureFormatField;
	private FileUtil fileUtil;
	
	private JTextField nameField;
	
	private static final int sanityLimit = 100;
	
	public SingleStructureImportPanel(
			JDialog dialog,
			StructureViewerManager viewerManager,
			FileUtil fileUtil){
		super(dialog, viewerManager);
		this.fileUtil = fileUtil;
			
		addLoadFilePanel();
		addModelNamePanel();
	}
	
	private void addLoadFilePanel(){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		structureFileButton = p.addFileParameter(
				"Structure file",
				null,
				"None",
				"Structure file",
				new String[]{},
				"",
				fileUtil);
		
		ActionListener updateModelNamelistener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Optional<File> f = structureFileButton.getMaybeFile();
				if(f.isPresent()){
					setToFreeModelName(f.get().getName());
				} else {
					setToFreeModelName("");
				}
			}
		};
		
		structureFileButton.addFileChosenListener(updateModelNamelistener);
		
		ViewerType viewerType = viewerManager.getViewerType();
		FormatCollection fileFormatCollection = 
				getFileFormatCollection(viewerType);
		structureFormatField = p.addChoosableParameter(
						"Format",
						fileFormatCollection.getOptions().toArray(new FileFormat[0]),
						null);
		
		ActionListener updateFormatListener = 
				new UpdateFileFormatListener(
						structureFileButton,
						structureFormatField,
						fileFormatCollection);
		
		structureFileButton.addFileChosenListener(updateFormatListener);
		
		this.add(p);	
	}
	
	protected FormatCollection getFileFormatCollection(ViewerType viewerType){
		return viewerType.getStructureFormatCollection();
	}
	
	public StructureLoader getModelLoader(){
		Path structurePath = structureFileButton.getMaybeFile()
				.orElseThrow(() -> new RuntimeException("Structure file not found"))
				.toPath();
		FileFormat format = getSelectedFileFormat()
				.orElseThrow(() -> new RuntimeException("No structure format selected"));
		return new SingleStructureLoader(structurePath, format.getStandardName());
	}
	
	public Optional<FileFormat> getSelectedFileFormat(){
		return Optional.ofNullable((FileFormat) structureFormatField.getSelectedItem());
	}
	
	protected void addModelNamePanel(){
		LabeledParametersPanel p = new LabeledParametersPanel();
		nameField = p.addTextParameter("Model name", "");
		this.add(p);
	}
	
	protected void setToFreeModelName(String name){
		name = getFreeModelName(name);
		setModelName(name);
	}
	
	protected void setModelName(String name){
		nameField.setText(name);
		dialog.pack();
	}
	
	
	
	public String getModelName(){
		String name = nameField.getText();
		if(name==null || name.isEmpty()){
			throw new IllegalArgumentException("Model name must not be empty");
		}
		name = name.replace(' ', '_');
		return name;
	}
	
	private String getFreeModelName(String base) {
		if(base==null || base.isEmpty()) return base;
		Set<String> currentModelNames = new HashSet<>(getCurrentLoadedModels());
		Integer i = 1;
		String name = base;
		while(currentModelNames.contains(name)){
			if(i>=sanityLimit) throw new RuntimeException("Could not find free model name");
			name = base + "-" + i.toString();
			i++;
		}
		return name;
	}
				
}
