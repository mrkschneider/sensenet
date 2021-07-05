package com.tcb.sensenet.internal.UI.panels.resultPanel;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.application.swing.CytoPanelState;

import com.tcb.sensenet.internal.UI.log.LogFrame;
import com.tcb.sensenet.internal.UI.panels.stylePanel.auto.edges.EdgeAutoStyleDialog;
import com.tcb.sensenet.internal.UI.panels.stylePanel.auto.nodes.NodeAutoStyleDialog;
import com.tcb.sensenet.internal.UI.table.TableView;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.UI.util.DefaultScrollPane;
import com.tcb.sensenet.internal.UI.util.TextPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.plot.Plot;
import com.tcb.sensenet.internal.util.HistoryList;
import com.tcb.sensenet.internal.util.JPanelUtil;
import com.tcb.cytoscape.cyLib.log.LogBuilder;

public class ResultPanel extends DefaultPanel implements CytoPanelComponent {
	private AppGlobals appGlobals;
	private CytoPanel parent;
	private JPanel contentPanel;
	private JScrollPane scrollPane;
	private HistoryList<JPanel> history;
	private int historyPos = 0;
	private int historySize = 10;
	private JPanel dummyPanel;
	
	private class ScrollPane extends DefaultScrollPane {

		public ScrollPane(int vsbPolicy, int hspPolicy) {
			super(vsbPolicy, hspPolicy);
		}
		
		public Dimension getPreferredSize() {
			return new Dimension(500,700);
		}
		
	}
	
	public ResultPanel(AppGlobals appGlobals){
		this.history = new HistoryList<>(historySize);
		this.appGlobals = appGlobals;
		this.parent = appGlobals.swingManager.getCytoPanel(getCytoPanelName());
		this.dummyPanel = new JPanel();
		
		appGlobals.resultPanelManager.register(this);	
		
		scrollPane = new ScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		this.add(scrollPane);
		this.add(new JPanel(),getDummyConstraints());
	}
		
	protected JPanel createHeaderPanel() {
		JPanel header = new DefaultPanel(getHorizontalPanelConstraints());
		addNodeAutoStyleButton(header);
		addEdgeAutoStyleButton(header);
		addHistoryBackButton(header);
		addHistoryForwardButton(header);
		addHistoryDeleteCurrentButton(header);
		header.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		return header;
	}
	
	protected GridBagConstraints getHorizontalPanelConstraints() {
		GridBagConstraints c = super.getDefaultConstraints();
		c.gridy = 0;
		c.gridx = GridBagConstraints.RELATIVE;
		return c;
	}
	
	protected GridBagConstraints getDummyConstraints() {
		GridBagConstraints dummyConstraints = getDefaultConstraints();
		dummyConstraints.weighty = 1.0;
		return dummyConstraints;
	}
		
	protected void addContentPanel(Container target) {
		GridBagConstraints c = getDefaultConstraints();
		contentPanel = new DefaultPanel(c);
		contentPanel.add(createHeaderPanel());
		
		history.add(contentPanel);
		historyPos = 0;

		scrollPane.setViewportView(contentPanel);
	}
	
	@Override
	protected GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = super.getDefaultConstraints();
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		return c;
	}
			
	private void setSize(){
		// Messing with the size seems to cause problems after the result panel
		// is closed by the user, so do nothing
	}
	
	public void clear(){		
		addContentPanel(scrollPane);		
	}
	
	public void detachContent() {
		JFrame frame = new JFrame();
		frame.setContentPane(scrollPane);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		this.revalidate();
	}
	
	private void showHistory(int idx) {
		contentPanel = history.get(historyPos);
		scrollPane.setViewportView(contentPanel);
		focusView();
	}
	
	public void historyBack() {
		historyPos = Math.min(
				history.getCurrentSize() - 1,
				historyPos + 1);
		showHistory(historyPos);
	}
	
	public void historyForward() {
		historyPos = Math.max(0, historyPos - 1);
		showHistory(historyPos);
	}
	
	public void historyDeleteCurrent() {
		history.remove(historyPos);
		if(history.getCurrentSize()==0) {
			scrollPane.setViewportView(null);
		} else {
			historyBack();
		}
	}
	
	public void showPlot(Plot plot, String header) throws Exception{
		plot.plot();
		JPanelUtil.setBorders(plot, header);
		contentPanel.add(plot);
		plot.addExportButton(appGlobals.fileUtil);
		setSize();
		focusView();	
	}
		
	public void showTable(TableView table, String header){
		JPanelUtil.setBorders(table, header);
		contentPanel.add(table);
		focusView();
	}
	
	public void addTopPanel(LogBuilder log){
		GridBagConstraints c = getDefaultConstraints();
		c.fill = GridBagConstraints.NONE;
		
		JPanel p = new DefaultPanel(getHorizontalPanelConstraints());
		addShowLogButton(p, log);
		contentPanel.add(p,c);
	}
	
	private void addHistoryBackButton(Container target) {
		JButton b = new JButton("<");
		b.addActionListener(e -> historyBack());
		target.add(b);
		focusView();
	}
	
	private void addHistoryForwardButton(Container target) {
		JButton b = new JButton(">");
		b.addActionListener(e -> historyForward());
		target.add(b);
		focusView();
	}
	
	private void addHistoryDeleteCurrentButton(Container target) {
		JButton b = new JButton("X");
		b.addActionListener(e -> historyDeleteCurrent());
		target.add(b);
		focusView();
	}
	
	private void addShowLogButton(Container target, LogBuilder log){		
		LogFrame logView = new LogFrame(log, appGlobals);
		JButton b = new JButton("Show log");
		
		ActionListener listener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				logView.setVisible(true);
			}
		};
		b.addActionListener(listener);
		
		target.add(b);
		focusView();
	}
	
	private void addDetachButton(Container target) {
		JButton b = new JButton("Detach");
		b.addActionListener(e -> detachContent());
		target.add(b);
		focusView();
	}
		
	private void addNodeAutoStyleButton(Container target){
		JButton b = new JButton("Node auto style");
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new NodeAutoStyleDialog(appGlobals);
				dialog.setVisible(true);
			}
		});
		target.add(b);
	}
	
	private void addEdgeAutoStyleButton(Container target){
		JButton b = new JButton("Edge auto style");
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new EdgeAutoStyleDialog(appGlobals);
				dialog.setVisible(true);
			}
		});
		target.add(b);
	}
	
	public void showText(TextPanel p){
		GridBagConstraints c = getDefaultConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		contentPanel.add(p,c);
		focusView();
	}
	
	private void focusView(){
		selectComponent(parent,this);
		if(parent.getState().equals(CytoPanelState.HIDE)){
			parent.setState(CytoPanelState.DOCK);
		}
		contentPanel.remove(dummyPanel);
		contentPanel.add(dummyPanel,getDummyConstraints());
		this.revalidate();
	}
	
	private void selectComponent(CytoPanel parent, Component component){
		parent.setSelectedIndex(parent.indexOfComponent(this));
	}
	
	@Override
	public Component getComponent() {
		return this;
	}
	
	@Override
	public CytoPanelName getCytoPanelName() {
		return CytoPanelName.EAST;
	}
	
	@Override
	public Icon getIcon() {
		return null;
	}
	
	@Override
	public String getTitle() {
		return "sensenet";
	}
	
	

}
