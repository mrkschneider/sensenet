package com.tcb.sensenet.internal.UI.panels.stylePanel;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.cytoscape.work.TaskFactory;

import com.tcb.sensenet.internal.UI.panels.stylePanel.listeners.ActionResetResidueNumberingListener;
import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.labeling.SetCustomResidueIndicesTaskConfig;
import com.tcb.sensenet.internal.task.labeling.factories.SetCustomResidueIndicesTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;

public class RenumberResiduesDialog extends DefaultDialog {
			
	private AppGlobals appGlobals;
		
	private JCheckBox shortNodeNamesBox;
	private JCheckBox removeDashesBox;
	private JCheckBox removeChainIdentifierBox;
	
	private JTextArea atomLabelExampleBox;
	private JTextArea residueLabelExampleBox;
	private JTextField chainBox;
	private JTextField residueStartBox;
	private JTextField residueEndBox;
	private JTextField offsetBox;

	public RenumberResiduesDialog(AppGlobals appGlobals) {
		this.appGlobals = appGlobals;
		
		this.setTitle("Renumber residues");
		
		addRenumberPanel(this);
		
		
		JPanel actionPanel = DialogUtil.createActionPanel(this::confirm, this::dispose);
		addResetNumberingButton(actionPanel);
		this.add(actionPanel);
				
		this.pack();
	}
	
	private void addRenumberPanel(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		chainBox = p.addTextParameter("Chain", "*");		
		residueStartBox = p.addTextParameter("First residue index", "1");
		residueEndBox = p.addTextParameter("Last residue index", "-1");
		offsetBox = p.addTextParameter("Offset", "0");
		target.add(p);
	}
	
	private void addResetNumberingButton(Container target){
		GridBagConstraints c = getDefaultDialogConstraints();
		c.insets = new Insets(5,0,5,0);
		
		JButton b = new JButton("Reset numbering");
		b.addActionListener(new ActionResetResidueNumberingListener(appGlobals){
			@Override
			public void actionPerformed(ActionEvent e) {
				super.actionPerformed(e);
				RenumberResiduesDialog.this.dispose();
			}
		});
		target.add(b,c);
	}
	
	
	private void confirm(){
		String chain = chainBox.getText().trim();
		Integer startResId = Integer.valueOf(residueStartBox.getText());
		Integer endResId = Integer.valueOf(residueEndBox.getText());
		Integer offset = Integer.valueOf(offsetBox.getText());
		SetCustomResidueIndicesTaskConfig config = 
				new SetCustomResidueIndicesTaskConfig(chain,startResId,endResId,offset,null);
		TaskFactory fac = new SetCustomResidueIndicesTaskFactory(config,appGlobals);
		appGlobals.taskManager.execute(fac.createTaskIterator());
		this.dispose();
	}
	
}
