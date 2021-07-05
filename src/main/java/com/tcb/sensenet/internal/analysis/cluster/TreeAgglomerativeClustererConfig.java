package com.tcb.sensenet.internal.analysis.cluster;

import com.tcb.cluster.linkage.LinkageStrategy;

public class TreeAgglomerativeClustererConfig implements TreeClustererConfig {

	private final TreeClusterMethod method = TreeClusterMethod.AGGLOMERATIVE;
	private LinkageStrategy linkage;
	
	
	public TreeAgglomerativeClustererConfig(LinkageStrategy linkage){
		this.linkage = linkage;
	}
	
	
	@Override
	public TreeClustererFactory getClustererFactory() {
		return new TreeAgglomerativeClustererFactory(linkage.getLinkage());
	}


	@Override
	public TreeClusterMethod getTreeClusterMethod() {
		return method;
	}
}
