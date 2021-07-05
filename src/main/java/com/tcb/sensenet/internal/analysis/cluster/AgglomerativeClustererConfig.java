package com.tcb.sensenet.internal.analysis.cluster;

import com.tcb.cluster.linkage.LinkageStrategy;

public class AgglomerativeClustererConfig implements ClustererConfig {

	private final ClusterMethod method = ClusterMethod.AGGLOMERATIVE;
	private LinkageStrategy linkage;
	
	
	public AgglomerativeClustererConfig(LinkageStrategy linkage){
		this.linkage = linkage;
	}
	
	
	@Override
	public ClustererFactory getClustererFactory() {
		return new AgglomerativeClustererFactory(linkage.getLinkage());
	}


	@Override
	public ClusterMethod getClusterMethod() {
		return method;
	}
}
