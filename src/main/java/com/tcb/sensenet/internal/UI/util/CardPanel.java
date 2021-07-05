package com.tcb.sensenet.internal.UI.util;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.tcb.common.util.SafeMap;

public class CardPanel<T extends Component, E extends Enum<E>> extends JPanel {
	
	private SafeMap<String,T> components;
	private E activeType;
	
	public CardPanel(){
		this.components = new SafeMap<>();
		this.activeType = null;
		this.setLayout(new DefaultCardLayout());
	}
		
	public void addCard(T card, E category){
		if(activeType==null) activeType = category;
		String key = category.name();
		components.put(key, card);
		super.add(card, key);
	}
	
	public void showCard(E category){
		activeType = category;
		String key = category.name();
		CardLayout layout = (CardLayout)this.getLayout();
		layout.show(this, key);
		Window frame = SwingUtilities.getWindowAncestor(this);
		if(frame!=null) {
			frame.pack();
		}
	}
			
	public T getActiveCard(){
		return components.get(activeType.name());
	}
	
	public E getActiveCardType(){
		return activeType;
	}
	
	public Integer getCardCount(){
		return components.size();
	}

}
