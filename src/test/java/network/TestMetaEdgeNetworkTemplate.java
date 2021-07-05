package network;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.SUIDFactory;
import org.mockito.Mockito;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.MetaContext;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public abstract class TestMetaEdgeNetworkTemplate {
	// Sets up a small network with a MetaEdge, source and target nodes, and two subEdges
	
	protected CyNetworkAdapter network;
	protected CyRootNetworkAdapter rootNetwork;
	protected CyRowAdapter headRow;
	protected List<CyRowAdapter> subEdgeRows;
	protected CyRowAdapter subEdgeRow1;
	protected CyRowAdapter subEdgeRow2;
	protected Long headSuid;
	protected CyEdge headEdge;
	protected CyNode headSourceNode;
	protected CyNode headTargetNode;
	protected CyRowAdapter headSourceNodeRow;
	protected CyRowAdapter headTargetNodeRow;
	protected CyTableAdapter rootNodeTable;
	protected Long headSourceNodeSuid;
	protected Long headTargetNodeSuid;
	protected String metaEdgeName;
	protected Set<Long> subEdgeSuids;
	protected Double weight;
	protected CyEdge subEdge1;
	protected CyEdge subEdge2;
	protected long subEdge1Suid;
	protected long subEdge2Suid;
	protected List<CyEdge> subEdges;
	protected MetaContext context;
	private List<Long> subEdgeSuidList;
	
	protected void setUpTestNetwork() {
		this.weight = 0.11d;
		setUpSuids();
		setUpNames();
		setUpNodesAndEdges();
		setUpRows();
		setUpRootNetwork();
		setUpNetwork();
		setUpRootNodeTable();
		
		fillNodesAndEdges();
		fillRows();
		fillRootNetwork();
		fillRootNodeTable();
		
		this.context = new MetaContext(rootNetwork, network);
		
	}
	
	private void setUpNames(){
		this.metaEdgeName = "testName";
	}
	
	
	
	private void setUpSuids(){
		this.headSuid = SUIDFactory.getNextSUID();
		this.headSourceNodeSuid = SUIDFactory.getNextSUID();
		this.headTargetNodeSuid = SUIDFactory.getNextSUID();
		
		this.subEdge1Suid = SUIDFactory.getNextSUID();
		this.subEdge2Suid = SUIDFactory.getNextSUID();
		
		this.subEdgeSuidList = Arrays.asList(subEdge1Suid,subEdge2Suid);
		this.subEdgeSuids = new HashSet<Long>(subEdgeSuidList);
	}
	
	
	private void setUpNodesAndEdges(){
		this.headSourceNode = Mockito.mock(CyNode.class);
		this.headTargetNode = Mockito.mock(CyNode.class);
		this.headEdge = Mockito.mock(CyEdge.class);
		this.subEdge1 = Mockito.mock(CyEdge.class);
		this.subEdge2 = Mockito.mock(CyEdge.class);
		this.subEdges = Arrays.asList(subEdge1,subEdge2);
	}
	
	private void fillNodesAndEdges(){
		when(headEdge.getSource()).thenReturn(headSourceNode);
		when(headEdge.getTarget()).thenReturn(headTargetNode);
		when(headEdge.getSUID()).thenReturn(headSuid);
		when(headSourceNode.getSUID()).thenReturn(headSourceNodeSuid);
		when(headTargetNode.getSUID()).thenReturn(headTargetNodeSuid);
		
		when(subEdge1.getSUID()).thenReturn(subEdge1Suid);
		when(subEdge2.getSUID()).thenReturn(subEdge2Suid);
	}
	
	
	
	
	private void setUpRows(){
		this.headRow = Mockito.mock(CyRowAdapter.class);
		this.subEdgeRow1 = Mockito.mock(CyRowAdapter.class);
		this.subEdgeRow2 = Mockito.mock(CyRowAdapter.class);
		this.subEdgeRows = Arrays.asList(subEdgeRow1,subEdgeRow2);
		this.headSourceNodeRow = Mockito.mock(CyRowAdapter.class);
		this.headTargetNodeRow = Mockito.mock(CyRowAdapter.class);
	}
	
	private void fillRows(){
		when(headRow.get(DefaultColumns.SUID, Long.class)).thenReturn(headSuid);
		when(headRow.get(DefaultColumns.SHARED_NAME, String.class)).thenReturn(metaEdgeName);
		when(headRow.get(AppColumns.WEIGHT, Double.class)).thenReturn(weight);
		when(subEdgeRows.get(0).get(DefaultColumns.SUID, Long.class)).thenReturn(subEdgeSuidList.get(0));
		when(subEdgeRows.get(1).get(DefaultColumns.SUID, Long.class)).thenReturn(subEdgeSuidList.get(1));
		
		when(headSourceNodeRow.get(DefaultColumns.SUID, Long.class)).thenReturn(headSourceNodeSuid);
		when(headTargetNodeRow.get(DefaultColumns.SUID, Long.class)).thenReturn(headTargetNodeSuid);
		

	}
	
	private void setUpRootNetwork(){
		this.rootNetwork = Mockito.mock(CyRootNetworkAdapter.class);
	}
	
	private void fillRootNetwork(){
		when(rootNetwork.getEdge(headSuid)).thenReturn(headEdge);
		when(rootNetwork.getSharedNodeTable()).thenReturn(rootNodeTable);

		when(rootNetwork.getRow(headSourceNode)).thenReturn(headSourceNodeRow);
		when(rootNetwork.getRow(headTargetNode)).thenReturn(headTargetNodeRow);
		
		when(rootNetwork.getEdgeList()).thenReturn(Arrays.asList(headEdge,subEdge1,subEdge2));
	}
	
	private void setUpNetwork(){
		this.network = Mockito.mock(CyNetworkAdapter.class);
	}
	
	
	private void setUpRootNodeTable(){
		this.rootNodeTable = Mockito.mock(CyTableAdapter.class);
	}
	
	private void fillRootNodeTable(){
		when(rootNodeTable.getRow(headSourceNodeSuid)).thenReturn(headSourceNodeRow);
		when(rootNodeTable.getRow(headTargetNodeSuid)).thenReturn(headTargetNodeRow);
	}
	
	protected abstract CyEdge setUpTestMetaEdge();
	
	
}
