package com.tcb.sensenet.internal.UI.panels.structureViewerPanel;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.structureViewer.factories.SyncResidueColorsTaskFactory;
import com.tcb.sensenet.internal.task.structureViewer.factories.TogglePauseNetworkLinkTaskFactory;
import com.tcb.sensenet.internal.task.structureViewer.factories.UnlinkModelFromStructureViewerTaskFactory;
import com.tcb.sensenet.internal.util.JPanelUtil;

public class ViewerStatusPanel extends DefaultPanel {
	private AppGlobals appGlobals;
		
	private static final String viewerNotConnectedLabelText = "not connected";
	private static final String viewerConnectedLabelText = "connected";
	
	private static final String noLinkedModelText = "not linked";
	
	private static final String pauseLinkButtonText = "Pause link";
	private static final String unpauseLinkButtonText = "Unpause link";
	
	private JLabel viewerConnectedLabel;
	private JButton connectViewerButton;
	private JButton linkNetworkButton;
	private JButton unlinkNetworkButton;
	private JLabel modelNameField;
	
	private JButton syncColorsButton;
	private JButton pauseLinkButton;
	
	public ViewerStatusPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
								
		addConnectPanel(this);
		addLinkModelsPanel(this);
		
		//JPanelUtil.setBorders(this, "Viewer status");
		
		
		appGlobals.stateManagers.viewerStatusPanelStateManager.register(this);
	}
	
	private void addConnectPanel(Container target){
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0,2));
		
		addConnectButton(p);
		addConnectStatusLabel(p);
		
		
		target.add(p);
	}
	
	private void addConnectButton(Container target){
		String buttonName = "Connect viewer";
		ActionListener listener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new ConnectStructureViewerDialog(appGlobals);
				dialog.setVisible(true);
			}
		};
		connectViewerButton = JPanelUtil.addButton(target, buttonName, listener);
	}
	
	private void addConnectStatusLabel(Container target){
		JLabel label = new JLabel(viewerNotConnectedLabelText);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		target.add(label);
		viewerConnectedLabel = label;
	}
	
	public void setViewerConnected(Boolean value){
		if(value.equals(true)){
			viewerConnectedLabel.setText(viewerConnectedLabelText);
		} else {
			viewerConnectedLabel.setText(viewerNotConnectedLabelText);
		}
	}
	
	private void addLinkModelsPanel(Container target) {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0,2));
		
		addModelNameLabel(p);
		addModelNameField(p);
		addLinkNetworkButton(p);
		addUnlinkNetworkButton(p);
		addTogglePauseLinkButton(p);
		addSyncResidueColorsButton(p);
		
		target.add(p);
	}
	
	private void addModelNameLabel(Container target){
		JLabel label = new JLabel("Model link");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		target.add(label);
	}
	
	private void addModelNameField(Container target){
		modelNameField = new JLabel(noLinkedModelText);
		modelNameField.setHorizontalAlignment(SwingConstants.CENTER);
		target.add(modelNameField);
	}
	
	public void setConnectedModelName(Optional<String> nameOpt){
		String name = nameOpt.orElse(noLinkedModelText);
		modelNameField.setText(name);
	}
	
	private void addLinkNetworkButton(Container target){
		String buttonName = "Link network";
		ActionListener listener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new LinkModelDialog(appGlobals);
				dialog.setVisible(true);
			}
		};
		linkNetworkButton = JPanelUtil.addButton(target, buttonName, listener);
	}
	
	private void addUnlinkNetworkButton(Container target){
		String buttonName = "Unlink network";
		ActionListener listener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				TaskIterator it = new UnlinkModelFromStructureViewerTaskFactory(appGlobals)
						.createTaskIterator();
				appGlobals.taskManager.execute(it);
			}
		};
		unlinkNetworkButton = JPanelUtil.addButton(target, buttonName, listener);
	}
	
	private void addTogglePauseLinkButton(Container target){
		JButton b = new JButton("Pause link");
		ActionListener listener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				TaskIterator it = 
						new TogglePauseNetworkLinkTaskFactory(appGlobals)
						.createTaskIterator();
				appGlobals.taskManager.execute(it);
			}
		};
		b.addActionListener(listener);
		target.add(b);
		pauseLinkButton = b;
	}
	
	public JButton getAddModelButton(){
		return linkNetworkButton;
	}
	
	public JButton getRemoveModelButton(){
		return unlinkNetworkButton;
	}
	
	public JButton getConnectButton(){
		return connectViewerButton;
	}
	
	public JButton getPauseLinkButton(){
		return pauseLinkButton;
	}
	
	public void setPauseLinkButtonText(Boolean paused){
		if(paused) pauseLinkButton.setText(unpauseLinkButtonText);
		else pauseLinkButton.setText(pauseLinkButtonText);
	}
			
	private void addSyncResidueColorsButton(Container target){
		String buttonName = "Transfer colors";
		ActionListener listener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				TaskIterator it = 
						new SyncResidueColorsTaskFactory(appGlobals)
						.createTaskIterator();
				appGlobals.taskManager.execute(it);
			}
		};
		syncColorsButton = JPanelUtil.addButton(target, buttonName, listener);
	}
	
	public JButton getTransferColorsButton(){
		return syncColorsButton;
	}
	
}
