package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.networkOptionsPanel.listeners;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextField;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;

public class UpdateDefaultNetworkNameObserver implements Observer {

	private JTextField field;
	private ImportNetworkDialog dialog;
	private String lastDefault;

	public UpdateDefaultNetworkNameObserver(ImportNetworkDialog dialog,
			JTextField field){
		this.field = field;
		this.dialog = dialog;
		this.lastDefault = "";
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		String text = field.getText();
		if(text==null || text.isEmpty() || text.equals(lastDefault)){
			String newDefault = getDefaultNetworkName();
			field.setText(newDefault);
			lastDefault = newDefault;
		}
	}
	
	private String getDefaultNetworkName(){
		String name = dialog.getImportPanel().getImportNetworkName();
		return name;
	}

}
