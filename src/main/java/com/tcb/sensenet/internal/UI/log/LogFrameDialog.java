package com.tcb.sensenet.internal.UI.log;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.util.CardPanel;
import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.LogStore;
import com.tcb.sensenet.internal.log.LogType;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.sensenet.internal.util.JPanelUtil;
import com.tcb.cytoscape.cyLib.log.LogBuilder;



public class LogFrameDialog extends DefaultDialog {
	private AppGlobals appGlobals;
	private CardPanel<AbstractLogSelectionPanel,LogType> logSelectionPanel;
	private JComboBox<LogType> logTypeBox;
	
	private static final AppProperty defaultLogTypeProperty = AppProperty.LOG_TYPE_DEFAULT;

	public LogFrameDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		addLogSelectionCardPanel(this);
				
		this.setTitle("Set log parameters");
		this.add(DialogUtil.createActionPanel(this::confirm, this::dispose));
		
		this.pack();
	}
	
	private void addLogSelectionCardPanel(Container target){
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(0,1));
		
		LogType defaultLogType = appGlobals.appProperties.getEnumOrDefault(LogType.class, defaultLogTypeProperty);
		
		LabeledParametersPanel p = new LabeledParametersPanel();
		logTypeBox = p.addChoosableParameter("Log category", LogType.values(), defaultLogType);
		logTypeBox.addActionListener((e) -> update());
				
		CardPanel<AbstractLogSelectionPanel,LogType> cards = new CardPanel<>();
		cards.addCard(new GlobalLogSelectionPanel(appGlobals), LogType.GLOBAL);
		cards.addCard(new TaskLogSelectionPanel(appGlobals), LogType.TASK);

		top.add(p);
		top.add(cards);
		
		logSelectionPanel = cards;
		target.add(top);
		update();
	}
	
	private void update(){
		LogType logType = (LogType) logTypeBox.getSelectedItem();
		logSelectionPanel.showCard(logType);
	}
	
	private void confirm(){
		LogType logType = (LogType) logTypeBox.getSelectedItem();
		appGlobals.appProperties.set(defaultLogTypeProperty, logType.name());
		
		LogBuilder log = logSelectionPanel.getActiveCard().getLog();	
		
		LogFrame p = new LogFrame(log,appGlobals);
		p.setAlwaysOnTop(true);
		p.setVisible(true);
		
		
		this.dispose();
	}
}
