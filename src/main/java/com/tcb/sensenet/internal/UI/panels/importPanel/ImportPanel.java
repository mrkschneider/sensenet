package com.tcb.sensenet.internal.UI.panels.importPanel;



import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.cytoscape.util.swing.FileChooserFilter;
import org.cytoscape.util.swing.FileUtil;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.log.LogFrameDialog;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.matrix.ExportNetworkMatrixDialog;
import com.tcb.sensenet.internal.UI.panels.importPanel.listeners.ActionCreateListener;
import com.tcb.sensenet.internal.UI.state.AlwaysActive;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.UI.util.SingletonErrorDialog;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.export.aif.ExportAifTaskConfig;
import com.tcb.sensenet.internal.task.export.aif.factories.ExportTilTaskFactory;

public class ImportPanel extends DefaultPanel {

	private AppGlobals appGlobals;
		
	@AlwaysActive
	private JButton importButton;
	
	private JButton networkSettingsButton;
	private JButton viewLogButton;

	private JButton exportButton;
	private JButton exportMatrixButton;
		 
	public ImportPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0,2));
		addCreateHbondNetworkButton(p);
		//addNetworkSettingsButton(p);
		addViewLogButton(p);
		addExportToAifButton(p);
				
		this.add(p);
		
		this.addDummyPanel();
						
	}
		
	private void addCreateHbondNetworkButton(JPanel target){
		JButton button = new JButton("Import network");
		ActionListener listener = new ActionCreateListener(appGlobals);
		button.addActionListener(listener);
		
		target.add(button);
		importButton = button;
	}
	
	private void addViewLogButton(JPanel target){
		JButton b = new JButton("Show log");
		ActionListener listener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				LogFrameDialog dialog = new LogFrameDialog(appGlobals);
				dialog.setVisible(true);;
			}
		};
		b.addActionListener(listener);
		
		viewLogButton = b;
		target.add(b);
	}
	
	private void addExportToAifButton(JPanel target){
		JButton button = new JButton("Export network");
		button.addActionListener((ActionEvent e) -> {
			try{
				FileChooserFilter filter = 
						new FileChooserFilter(".aif file", "aif");
				Optional<File> f = Optional.ofNullable(appGlobals.fileUtil.getFile(this, "write .aif file",
						FileUtil.SAVE, Arrays.asList(filter)));
				if(!f.isPresent()) return;
				ExportAifTaskConfig config = ExportAifTaskConfig.create(f.get().toPath());
				TaskIterator it = 
						new ExportTilTaskFactory(appGlobals)
						.createTaskIterator(config);
				appGlobals.taskManager.execute(it);
			} catch(Exception ex){
				SingletonErrorDialog.show(ex);
			}
		});
		
		target.add(button);
		exportButton = button;
	}
	
	private void addNetworkSettingsButton(JPanel target) {
		JButton button = new JButton("Network settings");
		button.addActionListener(e -> new MetaNetworkSettingsDialog(appGlobals).show());
		target.add(button);
		networkSettingsButton = button;
	}
		
}
