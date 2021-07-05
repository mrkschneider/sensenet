package com.tcb.sensenet.internal.UI.panels.appPanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;

import com.tcb.sensenet.internal.CyActivator;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.AnalysisPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.ImportPanel;
import com.tcb.sensenet.internal.UI.panels.showInteractionsPanel.ShowInteractionsPanel;
import com.tcb.sensenet.internal.UI.panels.structureViewerPanel.StructureViewerPanel;
import com.tcb.sensenet.internal.UI.panels.stylePanel.StylePanel;
import com.tcb.sensenet.internal.UI.panels.weightPanel.WeightPanel;
import com.tcb.sensenet.internal.UI.util.Accordeon;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.UI.util.DefaultScrollPane;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppMapProperty;
import com.tcb.cytoscape.cyLib.util.ContainerUtils;

public class AppPanel extends DefaultPanel implements CytoPanelComponent {

	private AppGlobals appGlobals;
	private JPanel contentPanel;
	private List<JPanel> subPanels;


	public AppPanel(AppGlobals appGlobals) {
		this.appGlobals = appGlobals;
		this.contentPanel = createContentPanel();
			
		addScrollPane(this, contentPanel);
		
		appGlobals.stateManagers.appPanelStateManager.register(this);
		
	}
	
	@Override
	protected GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = super.getDefaultConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		return c;
	}
	
	private void addScrollPane(JPanel target, Component content){
		JScrollPane scrollPane = new DefaultScrollPane(
				content,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		target.add(scrollPane);
	}
	
	private JPanel createContentPanel(){
		JPanel contentPanel = new DefaultPanel(getDefaultConstraints());
		//contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		JPanel createPanel = 
				new Accordeon("General",new ImportPanel(appGlobals));
		JPanel showInteractionsPanel = 
				new Accordeon(
						"Shown interactions",
						new ShowInteractionsPanel(appGlobals));
		JPanel stylePanel = 
				new Accordeon(
						"Style",
						new StylePanel(appGlobals));
		
		JPanel weightPanel = 
				new Accordeon(
						"Interaction weights",
						new WeightPanel(appGlobals), true);
		JPanel edgeAnalysisPanel = 
				new Accordeon(
						"Analysis",
						new AnalysisPanel(appGlobals));
		
		JPanel structureViewerPanel = 
				new Accordeon(
						"Structure visualization",
						new StructureViewerPanel(appGlobals));
				
		contentPanel.add(Box.createRigidArea(new Dimension(15,0)));
		
		subPanels = Arrays.asList(
				createPanel,
				showInteractionsPanel,
				weightPanel,
				edgeAnalysisPanel,
				structureViewerPanel,
				stylePanel);
		//subPanels.forEach(c -> c.setAlignmentX(LEFT_ALIGNMENT));
		subPanels.forEach(p -> contentPanel.add(p));
			
		setAccordeonOpenStates(subPanels);
		
		
		
		return contentPanel;
	}
	
	private void setAccordeonOpenStates(List<JPanel> panels){
		Map<String,Boolean> defaultOpenStates = appGlobals.appProperties.getMapOrDefault(
				AppMapProperty.UI_ACCORDEONS_OPEN, Boolean::valueOf);
		List<Accordeon> accordeonPanels = getAccordeonPanels();
		
		for(Accordeon a:accordeonPanels){
			JPanel contentPanel = a.getContentPanel();
			String panelClassName = contentPanel.getClass().getName();
			Boolean open = defaultOpenStates.getOrDefault(panelClassName, false);
			a.setOpen(open);
		}	
	}
		
	public List<Accordeon> getAccordeonPanels(){
		List<Component> components = new ArrayList<>();
		for(JPanel subPanel:subPanels){
			components.add(subPanel);
			components.addAll(ContainerUtils.getAllComponents(subPanel));
		}
		
		return components.stream()
				.filter(c -> c instanceof Accordeon)
				.map(c -> (Accordeon)c)
				.collect(Collectors.toList());
	}
	
	public CytoPanelName getCytoPanelName(){
		return CytoPanelName.WEST;
	}
	
	public Icon getIcon() {
		return null;
	}
	
	public String getTitle() {
		return CyActivator.APP_NAME_SHORT;
	}
	
	public Component getComponent() {
		return this;
	}
	
	public JPanel getContentPanel(){
		return contentPanel;
	}
	
}
