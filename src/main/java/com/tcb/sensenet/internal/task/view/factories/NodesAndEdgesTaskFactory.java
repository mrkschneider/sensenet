package com.tcb.sensenet.internal.task.view.factories;

import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.work.TaskIterator;

public interface NodesAndEdgesTaskFactory {
	public TaskIterator createTaskIterator(List<CyNode> nodes, List<CyEdge> edges);
	public TaskIterator createTaskIterator();
}
