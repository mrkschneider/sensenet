package com.tcb.sensenet.internal.UI.panels.stylePanel;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.panels.stylePanel.auto.nodes.NodeAutoStyleDialog;
import com.tcb.sensenet.internal.UI.panels.stylePanel.listeners.ActionRenumberResiduesListener;
import com.tcb.sensenet.internal.UI.panels.stylePanel.listeners.ActionSetCustomNodeLabelsListener;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.util.JPanelUtil;

public class NodeStylePanel extends DefaultPanel {
	private AppGlobals appGlobals;
	
	public NodeStylePanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
			
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0,1));
		addButtonPanel(p);
		
		this.add(p);
		
		addDummyPanel();
	}
	
	
	private void addButtonPanel(Container target){
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0,2));
		addAutoStyleButton(p);
		addCustomNodeLabelsButton(p);
		addRenumberResidueIndicesButton(p);
		
		target.add(p);
	}
	
	private void addCustomNodeLabelsButton(Container target){
		String buttonName = "Label format";
		ActionListener listener = new ActionSetCustomNodeLabelsListener(appGlobals);
		JPanelUtil.addButton(target, buttonName, listener);
	}
		
	private JButton addRenumberResidueIndicesButton(Container target){
		String buttonName = "Renumber";
		ActionListener listener = new ActionRenumberResiduesListener(appGlobals);
		return JPanelUtil.addButton(target, buttonName, listener);
	}
	
	private void addAutoStyleButton(Container target){
		JButton b = new JButton("Auto style");
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new NodeAutoStyleDialog(appGlobals);
				dialog.setVisible(true);
			}
		});
		target.add(b);
	}
					
}
