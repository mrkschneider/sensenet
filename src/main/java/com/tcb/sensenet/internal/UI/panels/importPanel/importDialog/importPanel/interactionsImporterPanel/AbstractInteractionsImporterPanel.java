package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.UI.util.FileButton;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.properties.AppPropertyUpdater;
import com.tcb.aifgen.importer.InteractionImporter;



public abstract class AbstractInteractionsImporterPanel extends JPanel {
		
	protected ImportNetworkDialog dialog;
	private Optional<InteractionImporter> importer;
	private AppProperties appProperties;

	protected abstract InteractionImporter createInteractionImporter() throws IOException;
	protected abstract FileButton getMainFileButton();
	
	protected static final String fileNotLoadedLabel = "none loaded";
	protected static final String optionalFileLabel = "optional";
		
	public AbstractInteractionsImporterPanel(ImportNetworkDialog parentDialog, AppProperties appProperties){
		this.importer = Optional.empty();
		this.dialog = parentDialog;
		this.appProperties = appProperties;
	}
	
	public InteractionImporter getInteractionImporter() throws IOException {
		if(!importer.isPresent()) {
			importer = Optional.of(createInteractionImporter());
		}
		return importer.get();
	}
	
	public String getMainFileName(){
			Optional<File> fileOpt = getMainFileButton().getMaybeFile();
			if(fileOpt.isPresent()){
				return fileOpt.get().getName();
			} else {
				return "";
			}
		}
		
	protected Set<String> getDefaultBackboneAtomNames(){
		return new HashSet<>(Arrays.asList("C","CA","O","N"));
	}
	
	protected Set<String> getIgnoreAtomNames(Boolean ignoreBackbone){
		Set<String> ignoreAtomNamesSet = new HashSet<>();
		if(ignoreBackbone) ignoreAtomNamesSet = getDefaultBackboneAtomNames();
		return ignoreAtomNamesSet;
	}
	
	protected JTextField addInteractionTypeField(LabeledParametersPanel p, AppProperty defaultInteractionTypeProperty){
		String defaultInteractionType = 
				appProperties.getOrDefault(defaultInteractionTypeProperty);
		JTextField field = p.addTextParameter("Interaction type", defaultInteractionType);
		return field;
	}
	
	protected String getAndUpdateInteractionType(JTextField field, AppProperty setDefaultProperty){
		String interactionType = field.getText().replaceAll("\\s", "");
		if(interactionType.isEmpty()){
			throw new IllegalArgumentException("Interaction type must not be empty");
		}
		AppPropertyUpdater updater = new AppPropertyUpdater(appProperties);
		updater.update(interactionType, setDefaultProperty);
		return interactionType;
	}
	
	protected void registerUpdateDialogListener(){
		FileButton b = getMainFileButton();
		b.addFileChosenListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.signalChanges();				
			}
		});
	}
}
