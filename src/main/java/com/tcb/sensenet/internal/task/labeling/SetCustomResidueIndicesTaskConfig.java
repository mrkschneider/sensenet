package com.tcb.sensenet.internal.task.labeling;

public class SetCustomResidueIndicesTaskConfig {
	private String chain;
	private Integer firstResIndex;
	private Integer lastResIndex;
	private Integer offset;
	private String  resInsert;
	
	public SetCustomResidueIndicesTaskConfig(
			String chain,
			Integer firstResIndex,
			Integer lastResIndex,
			Integer offset,
			String resInsert
			){
		this.chain = chain;
		this.firstResIndex = firstResIndex;
		this.lastResIndex = lastResIndex;
		this.offset = offset;
		this.resInsert = resInsert;
	}
	
	public String getChain(){
		return chain;
	}
	
	public Integer getFirstResIndex(){
		return firstResIndex;
	}
	
	public Integer getLastResIndex(){
		return lastResIndex;
	}
	
	public Integer getOffset(){
		return offset;
	}
	
	public String getResInsert(){
		return resInsert;
	}
}
