package com.tcb.sensenet.internal.UI.util;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import org.cytoscape.util.swing.FileChooserFilter;
import org.cytoscape.util.swing.FileUtil;

public class FileButton extends JButton {
	
	private FileUtil fileUtil;
	private String defaultLabel;
	private String fileDescription;
	private String[] fileSuffixes;
	private String helpText;
	private Optional<File> file;
	private List<ActionListener> listeners;

	public FileButton(
			String startPath,
			String defaultLabel,
			String fileDescription,
			String[] fileSuffixes,
			String helpText,
			FileUtil fileUtil){
		super(defaultLabel);
		this.fileUtil = fileUtil;
		this.defaultLabel = defaultLabel;
		this.fileDescription = fileDescription;
		this.fileSuffixes = fileSuffixes;
		this.helpText = helpText;
		this.listeners = new ArrayList<>();
		
		if(startPath!=null && !startPath.isEmpty()){
			this.file = Optional.of(new File(startPath));
		} else {
			this.file = Optional.empty();
		}
		
		addChooseFileListener();
		update();
	}
	
	public FileButton(
			String defaultLabel,
			String fileDescription,
			String[] fileSuffixes,
			String helpText,
			FileUtil fileUtil){
		this(null,defaultLabel,fileDescription,fileSuffixes,helpText,fileUtil);
	}
	
	public Optional<File> getMaybeFile(){
		return file;
	}
	
	public void setDefaultLabel(String label){
		defaultLabel = label;
	}
	
	private void addChooseFileListener(){
		this.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseFile();
				listeners.forEach(l -> l.actionPerformed(e));
			}
		});
	}
	
	private void chooseFile(){
		FileChooserFilter filter = new FileChooserFilter(fileDescription, getSuffixes());
		file = Optional.ofNullable(fileUtil.getFile(this, helpText,
				FileUtil.LOAD, Arrays.asList(filter)));
		update();
	}
	
	private String[] getSuffixes(){
		if(fileSuffixes.length > 0) return fileSuffixes;
		else return new String[]{""};
	}
	
	private void update(){
		updateLabels();
		updateWindow();
	}
	
	protected void updateLabels(){
		String label = defaultLabel;
		String shortLabel = defaultLabel;
		
		if(file.isPresent()){
			File f = file.get();
			label = getFileLabel(f);
			shortLabel = getShortFileLabel(f);
		}
		
		this.setText(shortLabel);
		this.setToolTipText(label);
	}
	
	protected String getFileLabel(File file){
		return file.getPath();
	}
	
	protected String getShortFileLabel(File file){
		return getFileLabel(file);
	}
	
	public void addFileChosenListener(ActionListener listener){
		listeners.add(listener);
	}
	
	
	private void updateWindow(){
		Window w = SwingUtilities.getWindowAncestor(this);
		if(w==null) return;
		w.pack();
	}
}
