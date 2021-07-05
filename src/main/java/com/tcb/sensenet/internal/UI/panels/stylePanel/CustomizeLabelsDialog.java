package com.tcb.sensenet.internal.UI.panels.stylePanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.cytoscape.work.TaskFactory;

import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.labeling.LabelSettings;
import com.tcb.sensenet.internal.labeling.examples.ExampleShortNodeLabelFactory;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.labeling.factories.SetCustomNodeLabelsTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.sensenet.internal.util.JPanelUtil;
import com.tcb.cytoscape.cyLib.UI.panel.WrapPanel;

public class CustomizeLabelsDialog extends DefaultDialog {
			
	private AppGlobals appGlobals;
		
	private JCheckBox shortNodeNamesBox;
	private JCheckBox removeDashesBox;
	private JCheckBox removeChainIdentifierBox;
	
	private JTextArea atomLabelExampleBox;
	private JTextArea residueLabelExampleBox;

	public CustomizeLabelsDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
				
		this.setTitle("Customize labels");
		
		addNodeLabelsPanel();
		addExamplePanel();
		
		this.add(DialogUtil.createActionPanel(this::confirm, this::cancel));
		
		this.pack();
	}
	
	public void addNodeLabelsPanel(){
		JPanel p = new JPanel();
		
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		shortNodeNamesBox = createResponsiveCheckbox("Short node names");
		removeDashesBox = createResponsiveCheckbox("Remove dashes");
		removeChainIdentifierBox = createResponsiveCheckbox("Remove chain identifiers");
		
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		LabelSettings previousLabelSettings = appGlobals.state.networkSettingsManager.get(metaNetwork).labelSettings;
		shortNodeNamesBox.setSelected(previousLabelSettings.useShortNames);
		removeDashesBox.setSelected(previousLabelSettings.removeDashes);
		removeChainIdentifierBox.setSelected(previousLabelSettings.removeChainIds);
		
		p.add(shortNodeNamesBox);
		p.add(removeDashesBox);
		p.add(removeChainIdentifierBox);
		
		JPanelUtil.setBorders(p, "Node labels");
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		this.add(p,c);
	}
	
	private JCheckBox createResponsiveCheckbox(String name){
		JCheckBox box = new JCheckBox(name);
		
		ActionListener l = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				updateExamples();
			}
			
		};
		
		box.addActionListener(l);
		return box;
	}
	
	public LabelSettings getLabelSettings(){
		LabelSettings s = new LabelSettings();
		s.useShortNames = shortNodeNamesBox.isSelected();
		s.removeDashes = removeDashesBox.isSelected();
		s.removeChainIds = removeChainIdentifierBox.isSelected();
		return s;
	}
	
	private void confirm(){
		LabelSettings settings = getLabelSettings();
		TaskFactory fac = new SetCustomNodeLabelsTaskFactory(appGlobals,settings);
		appGlobals.taskManager.execute(fac.createTaskIterator());
		this.dispose();
	}
	
	private void cancel(){
		dispose();
	}
			
	private void updateExamples(){
		LabelSettings settings = getLabelSettings();
		ExampleShortNodeLabelFactory labelFactory = new ExampleShortNodeLabelFactory(settings);
		atomLabelExampleBox.setText(labelFactory.createAtomExampleLabel());
		residueLabelExampleBox.setText(labelFactory.createResidueExampleLabel());
	}
	
	private void addExamplePanel(){
		JPanel p = new JPanel();
		
		p.setLayout(new GridLayout(2,2));
		p.setAlignmentX(CENTER_ALIGNMENT);
		p.setPreferredSize(new Dimension(250,20));
		JPanelUtil.setBorders(p, "Example");
		
		addExampleBoxes(p);
		
		updateExamples();
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.CENTER;
		
		this.add(p,c);
	}
	
	private void addExampleBoxes(JPanel p){
		
		WrapPanel<JLabel> residueLabelExampleLabel = new WrapPanel<JLabel>(new JLabel("Residue node"),0,10,0,10);
		residueLabelExampleLabel.setPreferredSize(new Dimension(200,40));
		
		residueLabelExampleBox = new JTextArea();
		
		p.add(residueLabelExampleLabel);
		p.add(residueLabelExampleBox);
		
		WrapPanel<JLabel> atomLabelExampleLabel = new WrapPanel<JLabel>(new JLabel("Atom node"),0,10,0,10);
		atomLabelExampleLabel.setPreferredSize(new Dimension(200,40));
		
		atomLabelExampleBox = new JTextArea();
		
		p.add(atomLabelExampleLabel);
		p.add(atomLabelExampleBox);
				
		
		/*c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = GridBagConstraints.RELATIVE;*/
		
		
		
	}
}
