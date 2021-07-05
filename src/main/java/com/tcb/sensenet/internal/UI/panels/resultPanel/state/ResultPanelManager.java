package com.tcb.sensenet.internal.UI.panels.resultPanel.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tcb.sensenet.internal.UI.panels.resultPanel.ResultPanel;

public class ResultPanelManager {
	private Optional<ResultPanel> resultPanel;
	private List<Runnable> hooks;
	private boolean activated = false;
	
	public ResultPanelManager(){
		this.resultPanel = Optional.empty();
		this.hooks = new ArrayList<>();
	}
	
	public void register(ResultPanel panel){
		this.resultPanel = Optional.of(panel);
	}
	
	public ResultPanel getResultPanel(){
		if(!activated) {
			for(Runnable hook:hooks) hook.run();
			activated = true;
		}
		return resultPanel.orElseThrow(() -> new RuntimeException("No ResultPanel registered"));
	}
	
	public void addActivationHook(Runnable f) {
		hooks.add(f);
	}
	
	public void clearActivationHooks() {
		hooks.clear();
	}
}
