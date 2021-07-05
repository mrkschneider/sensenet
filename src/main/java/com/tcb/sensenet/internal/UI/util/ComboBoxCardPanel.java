package com.tcb.sensenet.internal.UI.util;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Window;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.tcb.common.util.SafeMap;
import com.tcb.sensenet.internal.util.JPanelUtil;

public class ComboBoxCardPanel<T extends Enum<T>,U extends Component> extends DefaultPanel {

	private JComboBox<T> selectionBox;
	private CardPanel<U,T> cardPanel;
	
	public ComboBoxCardPanel(JComboBox<T> selectionBox){
		this.selectionBox = selectionBox;
		this.cardPanel = new CardPanel<>();
		
		this.add(cardPanel);
				
		selectionBox.addActionListener((e) -> updateCardPanel());
	}
				
	@Override
	protected GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = super.getDefaultConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		return c;
	}
	
	@SuppressWarnings("unchecked")
	private void updateCardPanel(){
		T selection = (T) selectionBox.getSelectedItem();
		cardPanel.showCard(selection);
	}
	
	public U getActiveCard(){
		return cardPanel.getActiveCard();
	}
	
	@SuppressWarnings("unchecked")
	public T getActiveSelection(){
		return (T) selectionBox.getSelectedItem();
	}
	
	public void addCard(T category, U card){
		cardPanel.addCard(card, category);
		int cardCount = cardPanel.getCardCount();
		int itemCount = selectionBox.getItemCount();
		if(cardCount >= itemCount) updateCardPanel();
	}
	
	
}
