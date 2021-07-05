package com.tcb.sensenet.internal.UI.panels.stylePanel;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.tcb.sensenet.internal.UI.panels.stylePanel.auto.edges.EdgeAutoStyleDialog;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.app.AppGlobals;

public class EdgeStylePanel extends DefaultPanel {
	private AppGlobals appGlobals;
	
	private JButton autoStyleButton;
	
	public EdgeStylePanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
			
		addAutoStyleButton(this);
		
		addDummyPanel();
	}
	
	
	private void addAutoStyleButton(Container target){
		JButton b = new JButton("Auto style");
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new EdgeAutoStyleDialog(appGlobals);
				dialog.setVisible(true);
			}
		});
		autoStyleButton = b;
		target.add(b);
	}
					
}
