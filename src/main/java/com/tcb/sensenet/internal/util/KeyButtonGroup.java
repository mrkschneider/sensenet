package com.tcb.sensenet.internal.util;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.ButtonGroup;

import com.tcb.common.util.ListFilter;

public class KeyButtonGroup extends ButtonGroup {

	 public void setSelected(String buttonKey, boolean select){
		 KeyRadioButton button = getRadioButton(buttonKey).get();
		 this.setSelected(button.getModel(),select);
	 }
	 
	 public KeyRadioButton getSelected(){
		 return ListFilter.singleton(
				 getRadioButtons()
				 .filter(b -> b.isSelected()==true)
				 .collect(Collectors.toList())).get();
	 }
	 
	 private Stream<KeyRadioButton> getRadioButtons(){
		 return Collections.list(this.getElements()).stream()
		 .filter(b -> b instanceof KeyRadioButton)
		 .map(b -> (KeyRadioButton)b);
	 }
	 
	 public void setOrClearSelection(String buttonKey, boolean select){
		 Optional<KeyRadioButton> button = getRadioButton(buttonKey);
		 if(button.isPresent()) this.setSelected(button.get().getModel(), select);
		 else this.clearSelection();
	 }
	 
	 public Optional<KeyRadioButton> getRadioButton(String key){
		 return ListFilter.singleton(
				getRadioButtons()
				 .filter(b -> b.getKey().equals(key))
				 .collect(Collectors.toList()));
	 }
 
 
}
