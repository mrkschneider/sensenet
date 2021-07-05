package com.tcb.sensenet.internal.UI.panels.stylePanel.auto;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

import org.cytoscape.view.vizmap.VisualMappingFunction;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.util.CardPanel;
import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.data.NetworkColumnStatistics;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.task.style.factories.CreateAutoStyleTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.sensenet.internal.util.JPanelUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;

public abstract class AbstractAutoStyleDialog<E extends Enum<E>> extends DefaultDialog {
	
	protected abstract E[] getAutoStyles();
	protected abstract CardPanel<AbstractAutoStylePanel,E> createCardPanel();
	protected abstract CyTableAdapter getTable();
	protected abstract E getDefaultAutoStyle();
	protected abstract AppProperty getDefaultStyleProperty();
	protected abstract AppProperty getDefaultStyleColumnProperty();
		
	protected AppGlobals appGlobals;
	
	private JComboBox<E> visualStyleBox;
	private JComboBox<String> mappingColumnBox;
	private CardPanel<AbstractAutoStylePanel,E> cards;
	
	public AbstractAutoStyleDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		addGeneralOptions(this);
		addCardsPanel(this);
				
		this.add(DialogUtil.createActionPanel(this::confirm, this::dispose));
		this.pack();
	}
	
	private void addGeneralOptions(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();

		visualStyleBox = p.addChoosableParameter(
				"Style property",
				getAutoStyles(),
				getDefaultAutoStyle());
		visualStyleBox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateActiveCard();
			}
		});
				
		String[] numberColumns = new NetworkColumnStatistics(getTable())
				.getColumns(Double.class)
				.toArray(new String[0]);
		String defaultColumn = appGlobals.appProperties
				.getOrNull(getDefaultStyleColumnProperty());
		
		mappingColumnBox = p.addChoosableParameter(
				"Column",
				numberColumns,
				defaultColumn);
		mappingColumnBox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateDefaultValues();
			}
		});
		
		target.add(p);
	}
	
	private void updateActiveCard(){
		E style = getSelectedAutoStyle();
		cards.showCard(style);
		this.pack();
		updateDefaultValues();
	}
	
	private E getSelectedAutoStyle(){
		@SuppressWarnings("unchecked")
		E style = (E)visualStyleBox.getSelectedItem();
		return style;
	}
	
	private void addCardsPanel(Container target){
		cards = createCardPanel();
		JPanelUtil.setBorders(cards, "Style settings");
		updateActiveCard();
		target.add(cards);
	}
	
	
	
	private void updateDefaultValues(){
		String mappingColumn = (String) mappingColumnBox.getSelectedItem();
		if(mappingColumn==null) return;
		cards.getActiveCard().updateTextFields(mappingColumn);
	}
	
	@Override
	protected GridBagConstraints getDefaultDialogConstraints(){
		GridBagConstraints c = super.getDefaultDialogConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		return c;
	}
	
	private void confirm(){
		E autoStyle = getSelectedAutoStyle();
		appGlobals.appProperties.set(getDefaultStyleProperty(), autoStyle.name());
		String mappingColumn = (String) mappingColumnBox.getSelectedItem();
		appGlobals.appProperties.set(getDefaultStyleColumnProperty(), mappingColumn);
		
		VisualMappingFunction<?,?> mappingFun = cards.getActiveCard()
				.getVisualMappingFunction(mappingColumn);
		TaskIterator it = 
				new CreateAutoStyleTaskFactory(appGlobals).createTaskIterator(mappingFun);
		appGlobals.taskManager.execute(it);
		this.dispose();
	}
}
