package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel;

import javax.swing.JTextField;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.properties.AppProperties;

public abstract class AbstractPdbImporterPanel extends AbstractInteractionsImporterPanel {
	
	public AbstractPdbImporterPanel(ImportNetworkDialog parentDialog, AppProperties appProperties) {
		super(parentDialog,appProperties);
	}

	protected static final String distanceCutoffFormatError = "Distance cut-off must be a positive number";
	protected static final String angleCutoffFormatError = "Angle cut-off must be either empty or a positive number";
	
	protected Double getDistanceCutoff(JTextField field){
		Double distanceCutoff;
		try{
		 distanceCutoff = Double.valueOf(field.getText());
		} catch(NumberFormatException e){
			throw new IllegalArgumentException(distanceCutoffFormatError);
		}
		if(distanceCutoff < 0.0){
			throw new IllegalArgumentException(distanceCutoffFormatError);
		}
		return distanceCutoff;
	}
	
	protected Double getAngleCutoff(JTextField field){
		String angleCutoffText = field.getText().trim();
		if(angleCutoffText==null || angleCutoffText.isEmpty()){
			return null;
		}
		Double angleCutoff;
		try{
			 angleCutoff = Double.valueOf(field.getText());
			} catch(NumberFormatException e){
				throw new IllegalArgumentException(angleCutoffFormatError);
			}
		return angleCutoff;
	}
		
}
